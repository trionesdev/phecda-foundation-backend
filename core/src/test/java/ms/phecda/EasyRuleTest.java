package ms.phecda;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;
import org.jeasy.rules.mvel.MVELRule;
import org.junit.jupiter.api.Test;

public class EasyRuleTest {

    @Test
    public void rule_test(){
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
        facts.put("name","张");
        facts.put("age","35");

        rulesEngine.fire(rules,facts);

    }

    @Test
    public void rule_builder_test(){
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
        facts.put("name","张");
        facts.put("age","11");

        rulesEngine.fire(rules,facts);

    }



}
