package ms.phecda.backend.core.domains.linkage.internal.factory.ruleaction.impl;

import ms.phecda.backend.core.domains.linkage.internal.factory.ruleaction.PhecdaRuleAction;
import ms.phecda.backend.core.domains.linkage.internal.rule.action.ActionArgs;
import ms.phecda.backend.core.domains.linkage.internal.rule.action.PhecdaAction;
import ms.phecda.backend.core.domains.linkage.internal.rule.action.PhecdaAction.TypeEnum;
import ms.phecda.backend.core.domains.linkage.internal.rule.action.PhecdaRuleActionComponent;

@PhecdaRuleActionComponent(type = TypeEnum.SERVICE_INVOCATION)
public class ServiceInvocationActionImpl implements PhecdaRuleAction {
    @Override
    public void execute(ActionArgs actionArgs, PhecdaAction phecdaAction) {

    }
}
