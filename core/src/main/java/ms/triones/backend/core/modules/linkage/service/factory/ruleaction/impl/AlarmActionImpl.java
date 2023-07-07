package ms.triones.backend.core.modules.linkage.service.factory.ruleaction.impl;

import ms.triones.backend.core.modules.linkage.service.factory.ruleaction.PhecdaRuleAction;
import ms.triones.backend.core.modules.linkage.support.rule.action.Action;
import ms.triones.backend.core.modules.linkage.support.rule.action.AlarmAction;
import ms.triones.backend.core.modules.linkage.support.rule.action.PhecdaRuleActionComponent;
import org.jeasy.rules.api.Facts;

@PhecdaRuleActionComponent(type = Action.TypeEnum.ALARM)
public class AlarmActionImpl implements PhecdaRuleAction {
    @Override
    public void execute(Facts facts, Action action) {
        AlarmAction alarmAction = (AlarmAction) action;

    }
}
