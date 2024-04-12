package ms.phecda.backend.core.domains.linkage.service.impl;

import com.google.common.collect.Lists;
import ms.phecda.BaseTest;
import ms.phecda.backend.core.domains.linkage.dao.entity.LinkageScene;
import ms.phecda.backend.core.domains.linkage.service.factory.ruleaction.RuleActionFactory;
import ms.phecda.backend.core.domains.linkage.support.rule.OperatorEnum;
import ms.phecda.backend.core.domains.linkage.support.rule.Scene;
import ms.phecda.backend.core.domains.linkage.support.rule.action.AlarmPhecdaAction;
import ms.phecda.backend.core.domains.linkage.support.rule.action.PhecdaAction;
import ms.phecda.backend.core.domains.linkage.support.rule.statecondition.ConditionTypeEnum;
import ms.phecda.backend.core.domains.linkage.support.rule.statecondition.StateCondition;
import ms.phecda.backend.core.domains.linkage.support.rule.statecondition.ThingPropertyValueCondition;
import ms.phecda.backend.core.domains.linkage.support.rule.trigger.EventTrigger;
import ms.phecda.backend.core.domains.linkage.support.rule.trigger.ThingPropertyReportTrigger;
import ms.phecda.backend.core.domains.linkage.support.rule.trigger.TriggerTypeEnum;
import ms.phecda.backend.core.domains.linkage.support.util.LinkageSceneUtils;
import ms.phecda.backend.core.domains.device.thing.valuetype.ValueTypeEnum;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Instant;
import java.util.Objects;

public class LinkageServiceTests extends BaseTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private LinkageSceneService linkageSceneService;
    @Autowired
    private RuleActionFactory ruleActionFactory;

    @Test
    public void rule_tests() {

//        List<OtherCondition> otherConditions = new ArrayList<>();
//        otherConditions.add(ThingModelPropertyCondition.builder()
//                .property("humidity").operator(ThingModelPropertyCondition.OperatorEnum.GT).params(Lists.newArrayList(10))
//                .build());
//        List<List<OtherCondition>> r = new ArrayList<>();
//        r.add(otherConditions);

        PhecdaAction phecdaAction = AlarmPhecdaAction.builder().type(PhecdaAction.TypeEnum.ALARM).build();

        LinkageScene linkageScene = LinkageScene.builder().id("test-rule")
                .scenes(Lists.newArrayList(
                        Scene.builder()
                                .eventTrigger( ThingPropertyReportTrigger.builder()
                                        .type(TriggerTypeEnum.THING_PROPERTY_REPORT)
                                        .identifier(ThingPropertyReportTrigger.Identifier.builder()
                                                .type(TriggerTypeEnum.THING_PROPERTY_REPORT)
                                                .productKey("ppp")
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
                                                                        .productKey("ppp")
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
                .actions(Lists.newArrayList(phecdaAction))
                .enabled(true)
                .build();

        DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
        Rules rules = new Rules();
        Rule rule = LinkageSceneUtils.createRule(linkageScene, ruleActionFactory);
        if (Objects.nonNull(rule)) {
            rules.register(rule);
            rules.register(rule);
        }

        Facts facts = new Facts();
        facts.put("productKey", "ppp");
        facts.put("deviceName", "a001");
        facts.put("temperature", 30);
        facts.put("humidity", "25");

        rulesEngine.fire(rules, facts);
    }

    @Test
    public void hash_test(){
        stringRedisTemplate.opsForHash().increment("ss","count",1);
        stringRedisTemplate.opsForHash().put("ss","start", Instant.now().toString());
    }

}
