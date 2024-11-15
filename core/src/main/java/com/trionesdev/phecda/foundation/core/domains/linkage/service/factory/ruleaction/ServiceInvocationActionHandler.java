package com.trionesdev.phecda.foundation.core.domains.linkage.service.factory.ruleaction;

import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.ActionArgs;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.PhecdaAction;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.PhecdaAction.TypeEnum;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.PhecdaRuleActionComponent;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.ServiceInvocationAction;
import org.apache.commons.lang3.StringUtils;

@PhecdaRuleActionComponent(type = TypeEnum.SERVICE_INVOCATION)
public class ServiceInvocationActionHandler implements PhecdaRuleActionHandler {
    @Override
    public void execute(ActionArgs actionArgs, PhecdaAction phecdaAction) {
        if (StringUtils.isBlank(actionArgs.getRuleName()) || StringUtils.isBlank(actionArgs.getDeviceName())) {
            return;
        }
        ServiceInvocationAction action = (ServiceInvocationAction) phecdaAction;

    }
}
