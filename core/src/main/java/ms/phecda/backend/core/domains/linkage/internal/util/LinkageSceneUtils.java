package ms.phecda.backend.core.domains.linkage.internal.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import ms.phecda.backend.core.domains.linkage.dao.entity.LinkageScene;
import ms.phecda.backend.core.domains.linkage.internal.factory.ruleaction.RuleActionFactory;
import ms.phecda.backend.core.domains.linkage.internal.rule.PhecdaRule;
import ms.phecda.backend.core.domains.linkage.internal.rule.Scene;
import ms.phecda.backend.core.domains.linkage.internal.rule.action.ActionArgs;
import ms.phecda.backend.core.domains.linkage.internal.rule.action.ActionTrigger;
import org.jeasy.rules.api.Rule;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class LinkageSceneUtils {

    public static String buildSceneRuleCondition(Scene scene) {
        StringBuilder ruleSb = new StringBuilder();
        ruleSb.append(scene.getEventTrigger().toRuleEl());
        List<String> operatorRules = Lists.newArrayList();
        if (CollectionUtil.isNotEmpty(scene.getConditions())) {
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
        }
        String operatorRulesStr = StrUtil.join(" || ", operatorRules);
        if (StrUtil.isNotBlank(operatorRulesStr)) {
            ruleSb.append(" && ( ").append(operatorRulesStr).append(" ) ");
        }
        return ruleSb.toString();
    }

    public static String buildScenesRuleCondition(List<Scene> scenes) {
        if (CollectionUtil.isEmpty(scenes)) {
            return null;
        }
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
        if (CollectionUtil.isEmpty(linkageScene.getScenes())) {
            return null;
        }
        if (CollectionUtil.isEmpty(linkageScene.getActions())) {
            return null;
        }

        return new PhecdaRule().name(linkageScene.getId())
                .when(buildScenesRuleCondition(linkageScene.getScenes()))
                .then(facts -> {
                    //region execute fire action
                    if (CollectionUtil.isNotEmpty(linkageScene.getActions())) {
                        if (!factory.canActionTrigger(linkageScene)) { //判断是否满足触发条件
                            return;
                        }
                        ActionArgs actionArgs = factory.factsToActionArgs(facts);
                        linkageScene.getActions().forEach(action -> {
                            factory.getAction(action.getType()).execute(actionArgs, action);
                        });
                    }
                    //endregion
                });
    }

    public static Boolean continuous(LinkageScene scene) {
        if (CollectionUtil.isEmpty(scene.getActions())) {
            return false;
        }
        if (Objects.equals(ActionTrigger.TriggerMode.CONTINUOUS, Optional.ofNullable(scene.getActionTrigger()).map(ActionTrigger::getTriggerMode).orElse(null))) {
            return true;
        }
        return false;
    }

}
