package ms.triones.backend.core.modules.linkage.service.factory.ruleaction;

import ms.triones.backend.core.modules.linkage.support.rule.action.Action;
import org.jeasy.rules.api.Facts;

public interface PhecdaRuleAction {

    void execute(Facts facts, Action action);
}
