package com.trionesdev.phecda.backend.core.domains.linkage.internal.factory.ruleaction.impl;

import cn.hutool.core.map.MapUtil;
import com.trionesdev.commons.core.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.trionesdev.phecda.backend.core.domains.alarm.dao.entity.Alarm;
import com.trionesdev.phecda.backend.core.domains.alarm.service.bo.AlarmCreateArgBO;
import com.trionesdev.phecda.backend.core.domains.alarm.service.impl.AlarmService;
import com.trionesdev.phecda.backend.core.domains.linkage.internal.factory.ruleaction.PhecdaRuleAction;
import com.trionesdev.phecda.backend.core.domains.linkage.internal.rule.action.ActionArgs;
import com.trionesdev.phecda.backend.core.domains.linkage.internal.rule.action.AlarmAction;
import com.trionesdev.phecda.backend.core.domains.linkage.internal.rule.action.PhecdaAction;
import com.trionesdev.phecda.backend.core.domains.linkage.internal.rule.action.PhecdaRuleActionComponent;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@PhecdaRuleActionComponent(type = PhecdaAction.TypeEnum.ALARM)
public class AlarmActionImpl implements PhecdaRuleAction {
    private final AlarmService alarmService;

    @Override
    public void execute(ActionArgs actionArgs, PhecdaAction phecdaAction) {
        if (StringUtils.isBlank(actionArgs.getRuleName()) || StringUtils.isBlank(actionArgs.getDeviceName())) {
            return;
        }
        AlarmAction alarmAction = (AlarmAction) phecdaAction;
        log.info("alarmAction: {}, facts: {}", alarmAction, JsonUtils.toJsonString(actionArgs));

        List<Alarm.Item> eventData = new ArrayList<>();
        if (MapUtil.isNotEmpty(actionArgs.getReadings())) {
            actionArgs.getReadings().forEach((k, v) -> {
                eventData.add(Alarm.Item.builder().identifier(v.getIdentifier()).label(v.getLabel()).value(v.getValue()).build());
            });
        }
        AlarmCreateArgBO alarm = AlarmCreateArgBO.builder().type(alarmAction.getAlarmType()).level(alarmAction.getAlarmLevel())
                .productKey(actionArgs.getProductKey()).deviceName(actionArgs.getDeviceName()).description(alarmAction.getDescription())
                .eventData(eventData)
                .build();
        alarmService.createAlarm(alarm);
    }


}
