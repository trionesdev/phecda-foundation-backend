package ms.phecda.foundation.core.domains.linkage.service.impl;

import com.google.common.collect.Lists;
import com.trionesdev.commons.core.util.JsonUtils;
import com.trionesdev.phecda.foundation.core.domains.linkage.service.impl.LinkageSceneService;
import ms.phecda.BaseTest;
import com.trionesdev.phecda.foundation.core.domains.linkage.dao.po.LinkageScenePO;
import com.trionesdev.phecda.foundation.core.domains.linkage.service.factory.RuleActionFactory;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.OperatorEnum;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.Scene;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.AlarmAction;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.PhecdaAction;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.statecondition.ConditionTypeEnum;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.statecondition.StateCondition;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.statecondition.ThingPropertyValueCondition;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.trigger.EventTrigger;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.trigger.ThingPropertyReportTrigger;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.trigger.TriggerTypeEnum;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.util.LinkageSceneUtils;
import com.trionesdev.phecda.model.device.thing.valuetype.ValueTypeEnum;
import com.trionesdev.phecda.infrastructure.configuration.cache.CacheTemplate;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class LinkageServiceTests extends BaseTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private LinkageSceneService linkageSceneService;
    @Autowired
    private RuleActionFactory ruleActionFactory;

    @Autowired
    private CacheTemplate<String, Map<String,String>> cacheTemplate;

    @Test
    public void rule_tests() {

//        List<OtherCondition> otherConditions = new ArrayList<>();
//        otherConditions.add(ThingModelPropertyCondition.builder()
//                .property("humidity").operator(ThingModelPropertyCondition.OperatorEnum.GT).params(Lists.newArrayList(10))
//                .build());
//        List<List<OtherCondition>> r = new ArrayList<>();
//        r.add(otherConditions);

        PhecdaAction phecdaAction = AlarmAction.builder().type(PhecdaAction.TypeEnum.ALARM).build();

        LinkageScenePO linkageScene = LinkageScenePO.builder().id("test-rule")
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

    @Test
    public void cache_test(){
        cacheTemplate.setValue("test", Map.of("a", "b"), 30, TimeUnit.SECONDS);
        System.out.println(JsonUtils.toJsonString(cacheTemplate.getValue("test")));
    }

}
