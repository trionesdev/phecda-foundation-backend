package ms.triones.backend.core.modules.linkage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import ms.triones.BaseTest;
import ms.triones.backend.core.modules.linkage.dao.entity.LinkageSceneCondition;
import ms.triones.backend.core.modules.linkage.support.rule.PhecdaRule;
import ms.triones.backend.core.modules.linkage.support.rule.filter.ThingModelExportCondition;
import ms.triones.backend.core.modules.linkage.support.rule.othercondition.OtherCondition;
import ms.triones.backend.core.modules.linkage.support.rule.othercondition.ThingModelPropertyCondition;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class LinkageServiceTests extends BaseTest {

    @Autowired
    private LinkageService linkageService;

    @Test
    public void rule_tests() {
        List<LinkageSceneCondition> conditions = new ArrayList<>();
        List<OtherCondition> otherConditions = new ArrayList<>();
        otherConditions.add(ThingModelPropertyCondition.builder()
                        .property("humidity").operator(ThingModelPropertyCondition.OperatorEnum.GT).params(Lists.newArrayList(10))
                        .build());
        List<List<OtherCondition>> r = new ArrayList<>();
        r.add(otherConditions);
        LinkageSceneCondition condition = LinkageSceneCondition.builder()
                .filterCondition(ThingModelExportCondition.builder()
                        .product("ppp").deviceName("a001")
                        .property("temperature").operator(ThingModelExportCondition.OperatorEnum.GT).params(Lists.newArrayList(20))
                        .build())
                .conditions( r )
                .build();
        conditions.add(condition);
        RulesEngine rulesEngine = new DefaultRulesEngine();
        Rules rules = new Rules();

        conditions.forEach(t->{
            if(CollectionUtil.isNotEmpty(t.getConditions())){
                t.getConditions().forEach(ct->{
                    List<String> conditionRowItem = Lists.newArrayList();
                    ct.forEach(conditionRow->{
                        conditionRowItem.add(conditionRow.toConditionEl());
                    });
                    String ruleWhen = "( "+t.getFilterCondition().toConditionEl()+" ) && ("+ StrUtil.join(" && ",conditionRowItem) +")";
                    Rule rule = new PhecdaRule().when(ruleWhen).then(facts -> {
                        linkageService.say();
                    });
                    rules.register(rule);
                });
            }else {

            }
        });


        Facts facts = new Facts();
        facts.put("product","ppp");
        facts.put("deviceName","a001");
        facts.put("temperature",30);
        facts.put("humidity","25");

        rulesEngine.fire(rules,facts);
    }

}
