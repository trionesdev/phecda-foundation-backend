package com.trionesdev.phecda.backend.core.domains.linkage.internal.factory.ruleaction.impl;

import com.trionesdev.phecda.backend.core.domains.linkage.internal.factory.ruleaction.PhecdaRuleAction;
import com.trionesdev.phecda.backend.core.domains.linkage.internal.rule.action.ActionArgs;
import com.trionesdev.phecda.backend.core.domains.linkage.internal.rule.action.PhecdaAction;
import com.trionesdev.phecda.backend.core.domains.linkage.internal.rule.action.PhecdaAction.TypeEnum;
import com.trionesdev.phecda.backend.core.domains.linkage.internal.rule.action.PhecdaRuleActionComponent;
import com.trionesdev.phecda.backend.core.domains.linkage.internal.rule.action.ServiceInvocationAction;
import org.apache.commons.lang3.StringUtils;

@PhecdaRuleActionComponent(type = TypeEnum.SERVICE_INVOCATION)
public class ServiceInvocationActionImpl implements PhecdaRuleAction {
    @Override
    public void execute(ActionArgs actionArgs, PhecdaAction phecdaAction) {
        if (StringUtils.isBlank(actionArgs.getRuleName()) || StringUtils.isBlank(actionArgs.getDeviceName())) {
            return;
        }
        ServiceInvocationAction action = (ServiceInvocationAction) phecdaAction;

    }
}
