package ms.triones.backend.core.modules.linkage.service.factory.ruleaction.impl;

import ms.triones.backend.core.modules.linkage.service.factory.ruleaction.PhecdaRuleAction;
import ms.triones.backend.core.modules.linkage.support.rule.action.Action;
import ms.triones.backend.core.modules.linkage.support.rule.action.NotificationAction;
import ms.triones.backend.core.modules.linkage.support.rule.action.PhecdaRuleActionComponent;
import org.jeasy.rules.api.Facts;

@PhecdaRuleActionComponent(type = Action.TypeEnum.NOTIFICATION)
public class NotificationActionImpl implements PhecdaRuleAction {
    @Override
    public void execute(Facts facts, Action action) {
        NotificationAction notificationAction = (NotificationAction) action;
    }
}
