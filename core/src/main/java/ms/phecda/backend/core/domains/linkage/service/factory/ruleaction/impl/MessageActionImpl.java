package ms.phecda.backend.core.domains.linkage.service.factory.ruleaction.impl;

import ms.phecda.backend.core.domains.linkage.service.factory.ruleaction.PhecdaRuleAction;
import ms.phecda.backend.core.domains.linkage.support.rule.action.Action;
import ms.phecda.backend.core.domains.linkage.support.rule.action.MessageAction;
import ms.phecda.backend.core.domains.linkage.support.rule.action.PhecdaRuleActionComponent;
import org.jeasy.rules.api.Facts;

@PhecdaRuleActionComponent(type = Action.TypeEnum.MESSAGE)
public class MessageActionImpl implements PhecdaRuleAction {
    @Override
    public void execute(Facts facts, Action action) {
        MessageAction messageAction = (MessageAction) action;
    }
}
