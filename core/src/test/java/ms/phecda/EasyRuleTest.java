package ms.phecda;

import com.google.common.collect.Lists;
import com.trionesdev.phecda.foundation.core.domains.linkage.dao.po.LinkageScenePO;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.OperatorEnum;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.Scene;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.statecondition.ConditionTypeEnum;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.statecondition.StateCondition;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.statecondition.ThingPropertyValueCondition;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.trigger.EventTrigger;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.trigger.ThingPropertyReportTrigger;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.trigger.TriggerTypeEnum;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.util.LinkageSceneUtils;
import com.trionesdev.phecda.infrastructure.rule.PhecdaRule;
import com.trionesdev.phecda.model.device.thing.valuetype.ValueTypeEnum;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;
import org.jeasy.rules.mvel.MVELRule;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class EasyRuleTest {

    @Test
    public void rule_test() {
        String condition = "name contains(\"张\")";
        String condition2 = " (age < 9) ||  (age > 10 && age <= 20)";
        String condition3 = " 30 <= age <= 40";
        //规则引擎
        RulesEngine rulesEngine = new DefaultRulesEngine();
        Rules rules = new Rules();
        //具体规则，当满足condition，然后输出对应的success
        Rule rule = new MVELRule().name("name rule").when(condition).then("System.out.println(\"name success\")  ").priority(2);
        Rule rule2 = new MVELRule().name("age rule").when(condition2).then("System.out.println(\"age success\")").priority(1);
        Rule rule3 = new MVELRule().name("age2 rule").when(condition3).then("System.out.println(\"age2 success\")").priority(1);

        rules.register(rule);
        rules.register(rule2);
        rules.register(rule3);
        //匹配规则的事实
        Facts facts = new Facts();
        facts.put("name", "张");
        facts.put("age", "35");

        rulesEngine.fire(rules, facts);

    }

    @Test
    public void rule_builder_test() {
        //规则引擎
        RulesEngine rulesEngine = new DefaultRulesEngine();
        Rules rules = new Rules();
        Rule rule = new RuleBuilder().name("name rule").when(facts -> {
            return true;
        }).then(facts -> {
            System.out.println("name success");
        }).priority(1).build();

        rules.register(rule);
        //匹配规则的事实
        Facts facts = new Facts();
        facts.put("name", "张");
        facts.put("age", "11");

        rulesEngine.fire(rules, facts);

    }


    @Test
    public void rule_builder_test2() {
        //规则引擎
        RulesEngine rulesEngine = new DefaultRulesEngine();
        Rules rules = new Rules();
        Rule rule = new RuleBuilder().name("name rule").when(facts -> {
            return true;
        }).then(facts -> {
            System.out.println("name success");
        }).priority(1).build();

        Rule rule2 = new MVELRule().name("name rule2").when(" 12 < age && age < 13").then("System.out.println(\"name success\")  ").priority(2);

        rules.register(rule);
        rules.register(rule2);
        //匹配规则的事实
        Facts facts = new Facts();
        facts.put("name", "张");
        facts.put("age", "11");
        facts.put("child", true);

        rulesEngine.fire(rules, facts);

    }


    @Test
    public void rule_tests() {
        LinkageScenePO linkageScene = LinkageScenePO.builder().id("test-rule")
                .scenes(Lists.newArrayList(
                        Scene.builder()
                                .eventTrigger(ThingPropertyReportTrigger.builder()
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

                .enabled(true)
                .build();

        DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.registerRuleListener(new MyRuleListener());
        Rules rules = new Rules();
        String condition = LinkageSceneUtils.buildScenesRuleCondition(linkageScene.getScenes());

        Rule rule2 = new MVELRule().name("name rule2").when(condition).then("System.out.println(\"name success\")  ").priority(2);
        Rule rule = new PhecdaRule<>().name("name rule2").when(condition).then("System.out.println(\"name success\")  ").priority(2);

        rules.register(rule);

        Facts facts = new Facts();
//        facts.put("productKey", "ppp");
        facts.put("deviceName", "a001");
        facts.put("temperature", 10);
        facts.put("humidity", "25");

        rulesEngine.fire(rules, facts);
    }

    public static class MyRuleListener implements RuleListener {

        @Override
        public void afterEvaluate(Rule rule, Facts facts, boolean evaluationResult) {
            System.out.println("result "+evaluationResult);
        }

        @Override
        public void onSuccess(Rule rule, Facts facts) {
            System.out.println("---success");
        }

        @Override
        public void onFailure(Rule rule, Facts facts, Exception exception) {
            System.out.println("fail");
        }
    }

    @Test
    public void rule_builder_test_3() {
        //规则引擎
        DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.registerRuleListener(new MyRuleListener());
        Rules rules = new Rules();
        Rule rule = new PhecdaRule<>().name("age rule").when("  obj.a2 > 9  ").then("System.out.println(\"age success\")").priority(1);

        rules.register(rule);
        //匹配规则的事实
        Facts facts = new Facts();
        Map<String, Object> map = new HashMap<>();
        map.put("a", 1);
//        map.put("af", null);
        facts.put("name", "张");
        facts.put("age", "11");
        facts.put("obj", map);
//        facts.put("obj.af",23);

        rulesEngine.fire(rules, facts);

    }


    @Test
    public void rule_builder_test_4() {
        //规则引擎
        DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.registerRuleListener(new MyRuleListener());
        Rules rules = new Rules();
        Rule rule = new MVELRule().name("age rule").when(" true && age<10 ").then("System.out.println(\"age success\")").priority(1);

        rules.register(rule);
        //匹配规则的事实
        Facts facts = new Facts();
        Map<String, Object> map = new HashMap<>();
        map.put("a", 1);
        facts.put("name", "+");
        facts.put("age", "11");
        facts.put("obj", map);

        rulesEngine.fire(rules, facts);

    }

    @Test
    public void test() {
        String s = "phecda/${productId}/${deviceName}/thing/property/post"
                .replaceAll("\\$\\{productId\\}", "productId")
                .replaceAll("\\$\\{deviceName\\}", "deviceName");
        System.out.println(s);
    }

}
