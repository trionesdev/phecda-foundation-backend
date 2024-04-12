package ms.phecda.backend.core.domains.linkage.service.factory.ruleaction;

import ms.phecda.backend.core.domains.linkage.support.rule.action.ActionArgs;
import ms.phecda.backend.core.domains.linkage.support.rule.action.PhecdaAction;
import org.jeasy.rules.api.Facts;

public interface PhecdaRuleAction {

    void execute(ActionArgs actionArgs, PhecdaAction phecdaAction);
}
