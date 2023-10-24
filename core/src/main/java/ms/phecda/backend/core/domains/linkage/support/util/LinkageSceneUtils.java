package ms.phecda.backend.core.domains.linkage.support.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import ms.phecda.backend.core.domains.linkage.service.factory.ruleaction.RuleActionFactory;
import ms.phecda.backend.core.domains.linkage.support.rule.PhecdaRule;
import ms.phecda.backend.core.domains.linkage.dao.entity.LinkageScene;
import ms.phecda.backend.core.domains.linkage.support.rule.Scene;
import org.jeasy.rules.api.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LinkageSceneUtils {

    public static String buildSceneRuleCondition(Scene scene) {
        StringBuilder ruleSb = new StringBuilder();
        ruleSb.append(scene.getEventTrigger().toRuleEl());
        List<String> operatorRules = Lists.newArrayList();
        scene.getConditions().forEach(c -> {
            List<String> stateRules = Lists.newArrayList();
            c.getChildren().forEach(cc -> {
                if (StrUtil.isNotBlank(cc.conditionEl())) {
                    stateRules.add(" ( " + cc.conditionEl() + " ) ");
                }
            });
            String stateRuleStr = StrUtil.join(" && ", stateRules);
            if (StrUtil.isNotBlank(stateRuleStr)) {
                operatorRules.add(stateRuleStr);
            }
        });
        String operatorRulesStr = StrUtil.join(" || ", operatorRules);
        if (StrUtil.isNotBlank(operatorRulesStr)) {
            ruleSb.append(" && ( ").append(operatorRulesStr).append(" ) ");
        }
        return ruleSb.toString();
    }

    public static  String buildScenesRuleCondition(List<Scene> scenes){
        List<String> sceneTriggerConditions = Lists.newArrayList();
        scenes.forEach(t -> {
            String sceneCondition = buildSceneRuleCondition(t);
            if (StrUtil.isNotBlank(sceneCondition)) {
                sceneTriggerConditions.add(" ( " + sceneCondition + " ) ");
            }
        });
       return StrUtil.join("||", sceneTriggerConditions);
    }

    public static Rule createRule(LinkageScene linkageScene, RuleActionFactory factory) {
        if (!BooleanUtil.isTrue(linkageScene.getEnabled())) {
            return null;
        }
        if (CollectionUtil.isEmpty(linkageScene.getScenes())){
            return null;
        }

        return new PhecdaRule().name(linkageScene.getId())
                .when(buildScenesRuleCondition(linkageScene.getScenes()))
                .then(facts -> {
            if (CollectionUtil.isNotEmpty(linkageScene.getActions())) {
                linkageScene.getActions().forEach(action -> {
                    factory.getAction(action.getType()).execute(facts, action);
                });
            }
        });
    }

}
