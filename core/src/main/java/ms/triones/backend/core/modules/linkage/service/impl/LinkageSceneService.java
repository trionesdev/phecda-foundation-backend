package ms.triones.backend.core.modules.linkage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.triones.backend.core.modules.linkage.dao.criteria.LinkageSceneCriteria;
import ms.triones.backend.core.modules.linkage.dao.entity.LinkageScene;
import ms.triones.backend.core.modules.linkage.manager.impl.LinkageSceneManager;
import ms.triones.backend.core.modules.linkage.service.factory.ruleaction.RuleActionFactory;
import ms.triones.backend.core.modules.linkage.support.util.LinkageSceneUtils;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LinkageSceneService {
    private final RulesEngine rulesEngine = new DefaultRulesEngine();
    private final Rules linkageRules = new Rules();
    private final LinkageSceneManager linkageSceneManager;
    private final RuleActionFactory ruleActionFactory;

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
        linkageSceneManager.deleteById(id);
    }

    public void sceneEnabledChange(String id, Boolean enabled) {
        linkageSceneManager.queryById(id).ifPresent(t -> {
            LinkageScene linkageScene = LinkageScene.builder().id(id).enabled(enabled).build();
            linkageSceneManager.updateById(linkageScene);
            if (enabled) {

            } else {

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
