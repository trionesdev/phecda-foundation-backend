package ms.phecda.backend.core.domains.linkage.internal.factory.ruleaction;

import ms.phecda.backend.core.domains.linkage.internal.rule.action.ActionArgs;
import ms.phecda.backend.core.domains.linkage.internal.rule.action.PhecdaAction;

public interface PhecdaRuleAction {

    void execute(ActionArgs actionArgs, PhecdaAction phecdaAction);
}
