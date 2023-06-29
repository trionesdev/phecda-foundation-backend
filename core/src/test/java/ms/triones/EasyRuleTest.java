package ms.triones;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;
import org.junit.jupiter.api.Test;

public class EasyRuleTest {

    @Test
    public void rule_test(){
        String condition = "name contains(\"张\") && age > 10";
        String condition2 = "age > 10 && age <= 20";
        //规则引擎
        RulesEngine rulesEngine = new DefaultRulesEngine();
        Rules rules = new Rules();
        //具体规则，当满足condition，然后输出对应的success
        Rule rule = new MVELRule().name("name rule").when(condition).when("age < 10").then("System.out.println(\"name success\")  ").priority(2);
        Rule rule2 = new MVELRule().name("age rule").when(condition2).then("System.out.println(\"age success\")").priority(1);

        rules.register(rule);
        rules.register(rule2);
        //匹配规则的事实
        Facts facts = new Facts();
        facts.put("name","张");
        facts.put("age","11");

        rulesEngine.fire(rules,facts);

    }

}
