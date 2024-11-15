package com.trionesdev.phecda.foundation.core.domains.linkage.service.impl;

import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.core.util.PageUtils;
import com.trionesdev.phecda.foundation.core.domains.linkage.dao.criteria.LinkageSceneCriteria;
import com.trionesdev.phecda.foundation.core.domains.linkage.dto.LinkageSceneDTO;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.LinkageDomainConvert;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.aggregate.entity.LinkageScene;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.RuleUtils;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.util.LinkageSceneUtils;
import com.trionesdev.phecda.foundation.core.domains.linkage.manager.impl.LinkageSceneManager;
import com.trionesdev.phecda.foundation.core.domains.linkage.service.factory.RuleActionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.RuleConstants.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class LinkageSceneService {
    private static final Map<String, Boolean> continuousMap = new ConcurrentHashMap<>();
    private final StringRedisTemplate stringRedisTemplate;
    private final LinkageDomainConvert convert;
    private final DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
    private final Rules linkageRules = new Rules();
    private final LinkageSceneManager linkageSceneManager;
    private final RuleActionFactory ruleActionFactory;

    {
        rulesEngine.registerRuleListener(new RuleListener() {
            @Override
            public void beforeExecute(Rule rule, Facts facts) {
                facts.put(FACT_RULE_NAME, rule.getName());
            }

            @Override
            public void afterEvaluate(Rule rule, Facts facts, boolean evaluationResult) {
                if (BooleanUtils.isTrue(continuousMap.get(rule.getName()))) {
                    //region 触发记录，如果是持续触发，则记录触发次数，否则，清空记录
                    if (evaluationResult) {
                        if (stringRedisTemplate.opsForHash().hasKey(RuleUtils.ruleContinuousKey(rule.getName()), RULE_CONTINUOUS_START_PROPERTY_KEY)) {
                            stringRedisTemplate.opsForHash().put(RuleUtils.ruleContinuousKey(rule.getName()), RULE_CONTINUOUS_START_PROPERTY_KEY, Instant.now().toString());
                        }
                        stringRedisTemplate.opsForHash().put(RuleUtils.ruleContinuousKey(rule.getName()), RULE_CONTINUOUS_COUNT_PROPERTY_KEY, 1);
                    } else {
                        stringRedisTemplate.opsForHash().delete(RuleUtils.ruleContinuousKey(rule.getName()));
                    }
                    //endregion
                }
            }
        });
    }


    /**
     * 创建场景
     *
     * @param scene
     */
    public void createScene(LinkageScene scene) {
        scene.setEnabled(false);
        linkageSceneManager.create(scene);
    }

    /**
     * 更新场景
     * 更新的时候，会先把规则下线
     *
     * @param scene
     */
    public void updateSceneById(LinkageScene scene) {
        linkageSceneManager.queryById(scene.getId()).ifPresent(t -> {
            if (t.getEnabled()) {
                unregisterRule(t.getId());
            }
        });
        scene.setEnabled(false);
        linkageSceneManager.updateById(scene);
    }


    /**
     * 删除场景
     *
     * @param id
     */
    public void deleteScene(String id) {
        unregisterRule(id);
        linkageSceneManager.deleteById(id);
    }

    private LinkageSceneDTO assembleLinkageScene(LinkageScene scene) {
        return convert.linkageSceneEntityToDto(scene);
    }

    public Optional<LinkageSceneDTO> querySceneById(String id) {
        return linkageSceneManager.queryById(id).map(this::assembleLinkageScene);
    }


    private List<LinkageSceneDTO> assembleLinkageScenes(List<LinkageScene> scenes) {
        if (CollectionUtils.isEmpty(scenes)) {
            return Collections.emptyList();
        }
        return scenes.stream().map(t -> {
            return convert.linkageSceneEntityToDto(t);
        }).toList();
    }

    public PageInfo<LinkageSceneDTO> page(LinkageSceneCriteria criteria) {
        var pageInfo = linkageSceneManager.page(criteria);
        return PageUtils.of(pageInfo, assembleLinkageScenes(pageInfo.getRows()));
    }

    /**
     * 场景启用禁用变更
     *
     * @param id
     * @param enabled
     */
    public void sceneEnabledChange(String id, Boolean enabled) {
        linkageSceneManager.queryById(id).ifPresent(t -> {
            LinkageScene linkageScene = LinkageScene.builder().id(id).enabled(enabled).build();
            linkageSceneManager.updateById(linkageScene);
            if (enabled) {
                registerRule(id);
            } else {
                unregisterRule(id);
            }
        });
    }

    public void registerRule(String id) {
        //TODO upgrade to broadcast message
        linkageSceneManager.queryById(id).ifPresent(this::registerRule);
    }

    public void unregisterRule(String id) {
        linkageRules.unregister(id);
        continuousMap.remove(id);
    }

    /**
     * 触发规则
     *
     * @param facts
     */
    public void rulesFire(Facts facts) {
        if (linkageRules.isEmpty()) {
            if (log.isInfoEnabled()) {
                log.info("[LinkageSceneService] no rules");
            }
            return;
        }
        rulesEngine.fire(linkageRules, facts);
    }

    /**
     * 注册所有规则
     */
    public void registerAllRules() {
        List<LinkageScene> scenes = linkageSceneManager.queryList(LinkageSceneCriteria.builder().enabled(true).build());
        if (CollectionUtils.isNotEmpty(scenes)) {
            scenes.forEach(this::registerRule);
        }
    }

    public void registerRule(LinkageScene scene) {
        Rule rule = LinkageSceneUtils.createRule(scene, ruleActionFactory);
        if (Objects.nonNull(rule)) {
            if (LinkageSceneUtils.continuous(scene)) {
                continuousMap.put(rule.getName(), true);
            }
            linkageRules.register(rule);
        }
    }

}
