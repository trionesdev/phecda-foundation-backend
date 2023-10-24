package ms.phecda.backend.core.domains.linkage.service.impl;

import com.google.common.collect.Lists;
import ms.phecda.BaseTest;
import ms.phecda.backend.core.domains.linkage.dao.entity.LinkageScene;
import ms.phecda.backend.core.domains.linkage.service.factory.ruleaction.RuleActionFactory;
import ms.phecda.backend.core.domains.linkage.support.rule.OperatorEnum;
import ms.phecda.backend.core.domains.linkage.support.rule.Scene;
import ms.phecda.backend.core.domains.linkage.support.rule.action.Action;
import ms.phecda.backend.core.domains.linkage.support.rule.action.AlarmAction;
import ms.phecda.backend.core.domains.linkage.support.rule.filter.ThingModelExportCondition;
import ms.phecda.backend.core.domains.linkage.support.rule.othercondition.OtherCondition;
import ms.phecda.backend.core.domains.linkage.support.rule.othercondition.ThingModelPropertyCondition;
import ms.phecda.backend.core.domains.linkage.support.rule.statecondition.ConditionTypeEnum;
import ms.phecda.backend.core.domains.linkage.support.rule.statecondition.StateCondition;
import ms.phecda.backend.core.domains.linkage.support.rule.statecondition.ThingPropertyValueCondition;
import ms.phecda.backend.core.domains.linkage.support.rule.trigger.EventTrigger;
import ms.phecda.backend.core.domains.linkage.support.rule.trigger.ThingPropertyReportTrigger;
import ms.phecda.backend.core.domains.linkage.support.rule.trigger.TriggerTypeEnum;
import ms.phecda.backend.core.domains.linkage.support.util.LinkageSceneUtils;
import ms.phecda.edge.base.commons.valuetype.ValueTypeEnum;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LinkageServiceTests extends BaseTest {

    @Autowired
    private LinkageSceneService linkageSceneService;
    @Autowired
    private RuleActionFactory ruleActionFactory;

    @Test
    public void rule_tests() {
        List<LinkageScene> conditions = new ArrayList<>();
        List<OtherCondition> otherConditions = new ArrayList<>();
        otherConditions.add(ThingModelPropertyCondition.builder()
                .property("humidity").operator(ThingModelPropertyCondition.OperatorEnum.GT).params(Lists.newArrayList(10))
                .build());
        List<List<OtherCondition>> r = new ArrayList<>();
        r.add(otherConditions);

        Action action = AlarmAction.builder().type(Action.TypeEnum.ALARM).deviceName("ssss").build();

        LinkageScene linkageScene = LinkageScene.builder().id("test-rule")
                .scenes(Lists.newArrayList(
                        Scene.builder()
                                .eventTrigger( ThingPropertyReportTrigger.builder()
                                        .type(TriggerTypeEnum.THING_PROPERTY_REPORT)
                                        .identifier(ThingPropertyReportTrigger.Identifier.builder()
                                                .type(TriggerTypeEnum.THING_PROPERTY_REPORT)
                                                .product("ppp")
                                                .deviceName("a001")
                                                .property("humidity")
                                                .build())
                                        .filter(EventTrigger.EventFilter.builder()
                                                .operator(OperatorEnum.GT)
                                                .valueType(ValueTypeEnum.INT)
                                                .args(Lists.newArrayList("20"))
                                                .build())
                                        .build())
                                .conditions(Lists.newArrayList(
                                        Scene.OperatorCondition.builder()
                                                .children(Lists.newArrayList(
                                                        ThingPropertyValueCondition.builder()
                                                                .stateIdentifier(ThingPropertyValueCondition.StateIdentifier.builder()
                                                                        .type(ConditionTypeEnum.THING_PROPERTY_VALUE)
                                                                        .product("ppp")
                                                                        .deviceName("a001")
                                                                        .build())
                                                                .condition(StateCondition.Condition.builder()
                                                                        .valuePath("temperature")
                                                                        .operator(OperatorEnum.GT)
                                                                        .valueType(ValueTypeEnum.INT)
                                                                        .args(Lists.newArrayList("20"))
                                                                        .build())
                                                                .build()
                                                ))
                                                .build()
                                ))
                                .build()
                ))
                .conditions(r)
                .actions(Lists.newArrayList(action))
                .enabled(true)
                .build();

        RulesEngine rulesEngine = new DefaultRulesEngine();
        Rules rules = new Rules();
        Rule rule = LinkageSceneUtils.createRule(linkageScene, ruleActionFactory);
        if (Objects.nonNull(rule)) {
            rules.register(rule);
            rules.register(rule);
        }

        Facts facts = new Facts();
        facts.put("product", "ppp");
        facts.put("deviceName", "a001");
        facts.put("temperature", 30);
        facts.put("humidity", "25");

        rulesEngine.fire(rules, facts);
    }

}
