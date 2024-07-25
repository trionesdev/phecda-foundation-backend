package ms.phecda.backend.core.domains.linkage.internal.factory.ruleaction.impl;

import ms.phecda.backend.core.domains.linkage.internal.factory.ruleaction.PhecdaRuleAction;
import ms.phecda.backend.core.domains.linkage.internal.rule.action.ActionArgs;
import ms.phecda.backend.core.domains.linkage.internal.rule.action.PhecdaAction;
import ms.phecda.backend.core.domains.linkage.internal.rule.action.PhecdaAction.TypeEnum;
import ms.phecda.backend.core.domains.linkage.internal.rule.action.PhecdaRuleActionComponent;
import ms.phecda.backend.core.domains.linkage.internal.rule.action.ServiceInvocationAction;
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
