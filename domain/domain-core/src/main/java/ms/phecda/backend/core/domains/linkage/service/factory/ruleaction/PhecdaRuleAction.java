package ms.phecda.backend.core.domains.linkage.service.factory.ruleaction;

import ms.phecda.backend.core.domains.linkage.support.rule.action.Action;
import org.jeasy.rules.api.Facts;

public interface PhecdaRuleAction {

    void execute(Facts facts, Action action);
}
