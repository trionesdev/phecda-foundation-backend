package ms.triones.backend.core.modules.linkage.support.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import ms.triones.backend.core.modules.linkage.dao.entity.LinkageScene;
import ms.triones.backend.core.modules.linkage.service.factory.ruleaction.RuleActionFactory;
import ms.triones.backend.core.modules.linkage.support.rule.PhecdaRule;
import org.jeasy.rules.api.Rule;

import java.util.ArrayList;
import java.util.List;

public class LinkageSceneUtils {

    public static Rule createRule(LinkageScene linkageScene, RuleActionFactory factory) {
        if (!BooleanUtil.isTrue(linkageScene.getEnabled())) {
            return null;
        }

        List<String> otherConditionElGroups = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(linkageScene.getConditions())) {

            linkageScene.getConditions().forEach(otherConditionGroup -> {

                if (CollectionUtil.isNotEmpty(otherConditionGroup)) {
                    List<String> otherConditionEls = Lists.newArrayList();
                    otherConditionGroup.forEach(otherCondition -> {
                        otherConditionEls.add(otherCondition.toConditionEl());
                    });
                    otherConditionElGroups.add(" ( " + StrUtil.join(" && ", otherConditionEls) + " ) ");
                }

            });
        }
        StringBuilder whenEl = new StringBuilder(" ( " + linkageScene.getFilterCondition().toConditionEl() + " ) ");
        if (CollectionUtil.isNotEmpty(otherConditionElGroups)) {
            whenEl.append(" && ").append(" ( ").append(StrUtil.join(" || ", otherConditionElGroups)).append(" ) ");
        }

        return new PhecdaRule().name(linkageScene.getId()).when(whenEl.toString()).then(facts -> {
            if (CollectionUtil.isNotEmpty(linkageScene.getActions())) {
                linkageScene.getActions().forEach(action -> {
                    factory.getAction(action.getType()).execute(facts, action);
                });
            }
        });
    }

}
