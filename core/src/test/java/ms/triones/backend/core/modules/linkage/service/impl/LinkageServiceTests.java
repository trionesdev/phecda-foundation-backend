package ms.triones.backend.core.modules.linkage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import ms.triones.BaseTest;
import ms.triones.backend.core.modules.linkage.dao.entity.LinkageScene;
import ms.triones.backend.core.modules.linkage.service.factory.ruleaction.RuleActionFactory;
import ms.triones.backend.core.modules.linkage.support.rule.PhecdaRule;
import ms.triones.backend.core.modules.linkage.support.rule.action.Action;
import ms.triones.backend.core.modules.linkage.support.rule.action.AlarmAction;
import ms.triones.backend.core.modules.linkage.support.rule.filter.ThingModelExportCondition;
import ms.triones.backend.core.modules.linkage.support.rule.othercondition.OtherCondition;
import ms.triones.backend.core.modules.linkage.support.rule.othercondition.ThingModelPropertyCondition;
import ms.triones.backend.core.modules.linkage.support.util.LinkageSceneUtils;
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
                .filterCondition(ThingModelExportCondition.builder()
                        .product("ppp").deviceName("a001")
                        .property("temperature").operator(ThingModelExportCondition.OperatorEnum.GT).params(Lists.newArrayList(20))
                        .build())
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
