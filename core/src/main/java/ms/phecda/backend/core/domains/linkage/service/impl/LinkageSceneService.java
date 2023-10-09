package ms.phecda.backend.core.domains.linkage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.moensun.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.domains.linkage.dao.criteria.LinkageSceneCriteria;
import ms.phecda.backend.core.domains.linkage.manager.impl.LinkageSceneManager;
import ms.phecda.backend.core.domains.linkage.dao.entity.LinkageScene;
import ms.phecda.backend.core.domains.linkage.service.factory.ruleaction.RuleActionFactory;
import ms.phecda.backend.core.domains.linkage.support.util.LinkageSceneUtils;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LinkageSceneService {
    private final DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
    private final Rules linkageRules = new Rules();
    private final LinkageSceneManager linkageSceneManager;
    private final RuleActionFactory ruleActionFactory;

    {
        rulesEngine.registerRuleListener(new RuleListener() {
            @Override
            public void beforeExecute(Rule rule, Facts facts) {
                facts.put("ruleName", rule.getName());
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
        linkageSceneManager.queryById(id).ifPresent(t -> {
            Rule rule = LinkageSceneUtils.createRule(t, ruleActionFactory);
            if (Objects.nonNull(rule)) {
                linkageRules.register(rule);
            }
        });
    }

    public void unregisterRule(String id) {
        linkageRules.unregister(id);
    }

    /**
     * 触发规则
     *
     * @param facts
     */
    public void rulesFire(Facts facts) {
        rulesEngine.fire(linkageRules, facts);
    }

    /**
     * 注册所有规则
     */
    public void registerAllRules() {
        List<LinkageScene> scenes = linkageSceneManager.queryList(LinkageSceneCriteria.builder().enabled(true).build());
        if (CollectionUtil.isNotEmpty(scenes)) {
            scenes.forEach(scene -> {
                Rule rule = LinkageSceneUtils.createRule(scene, ruleActionFactory);
                if (Objects.nonNull(rule)) {
                    linkageRules.register(rule);
                }
            });
        }
    }

}
