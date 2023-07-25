package ms.triones.backend.core.modules.linkage.service.factory.ruleaction.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.triones.backend.core.modules.alarm.service.impl.AlarmLogService;
import ms.triones.backend.core.modules.linkage.service.factory.ruleaction.PhecdaRuleAction;
import ms.triones.backend.core.modules.linkage.support.rule.action.Action;
import ms.triones.backend.core.modules.linkage.support.rule.action.AlarmAction;
import ms.triones.backend.core.modules.linkage.support.rule.action.PhecdaRuleActionComponent;
import org.jeasy.rules.api.Facts;

@Slf4j
@RequiredArgsConstructor
@PhecdaRuleActionComponent(type = Action.TypeEnum.ALARM)
public class AlarmActionImpl implements PhecdaRuleAction {
    private final AlarmLogService alarmLogService;

    @Override
    public void execute(Facts facts, Action action) {
        AlarmAction alarmAction = (AlarmAction) action;
        log.info("alarmAction: {}, facts: {}", alarmAction, facts);
    }
}
