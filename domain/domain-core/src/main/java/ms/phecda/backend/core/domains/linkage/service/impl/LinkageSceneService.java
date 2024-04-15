package ms.phecda.backend.core.domains.linkage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.BooleanUtil;
import com.trionesdev.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.domains.linkage.dao.criteria.LinkageSceneCriteria;
import ms.phecda.backend.core.domains.linkage.manager.impl.LinkageSceneManager;
import ms.phecda.backend.core.domains.linkage.dao.entity.LinkageScene;
import ms.phecda.backend.core.domains.linkage.service.factory.ruleaction.RuleActionFactory;
import ms.phecda.backend.core.domains.linkage.support.rule.RuleUtils;
import ms.phecda.backend.core.domains.linkage.support.util.LinkageSceneUtils;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static ms.phecda.backend.core.domains.linkage.support.rule.RuleConstants.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class LinkageSceneService {
    private static final Map<String, Boolean> continuousMap = new ConcurrentHashMap<>();
    private final StringRedisTemplate stringRedisTemplate;
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
                if (BooleanUtil.isTrue(continuousMap.get(rule.getName()))) {
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

    public PageInfo<LinkageScene> page(LinkageSceneCriteria criteria) {
        return linkageSceneManager.page(criteria);
    }

    public void createScene(LinkageScene scene) {
        scene.setEnabled(false);
        linkageSceneManager.create(scene);
    }

    public void updateSceneById(LinkageScene scene) {
        linkageSceneManager.queryById(scene.getId()).ifPresent(t -> {
            if (t.getEnabled()) {
                unregisterRule(t.getId());
            }
        });
        scene.setEnabled(false);
        linkageSceneManager.updateById(scene);
    }

    public Optional<LinkageScene> querySceneById(String id) {
        return linkageSceneManager.queryById(id);
    }

    public void deleteScene(String id) {
        unregisterRule(id);
        linkageSceneManager.deleteById(id);
    }

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
        if (CollectionUtil.isEmpty(linkageRules)) {
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
        if (CollectionUtil.isNotEmpty(scenes)) {
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
