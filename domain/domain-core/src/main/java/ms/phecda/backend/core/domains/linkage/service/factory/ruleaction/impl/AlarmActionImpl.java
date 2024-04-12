package ms.phecda.backend.core.domains.linkage.service.factory.ruleaction.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.trionesdev.commons.core.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.domains.alarm.dao.entity.Alarm;
import ms.phecda.backend.core.domains.alarm.service.bo.AlarmCreateArgBO;
import ms.phecda.backend.core.domains.alarm.service.impl.AlarmService;
import ms.phecda.backend.core.domains.linkage.service.factory.ruleaction.PhecdaRuleAction;
import ms.phecda.backend.core.domains.linkage.support.rule.RuleUtils;
import ms.phecda.backend.core.domains.linkage.support.rule.action.ActionArgs;
import ms.phecda.backend.core.domains.linkage.support.rule.action.AlarmPhecdaAction;
import ms.phecda.backend.core.domains.linkage.support.rule.action.PhecdaAction;
import ms.phecda.backend.core.domains.linkage.support.rule.action.PhecdaRuleActionComponent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ms.phecda.backend.core.domains.linkage.support.rule.RuleConstants.RULE_CONTINUOUS_START_PROPERTY_KEY;

@Slf4j
@RequiredArgsConstructor
@PhecdaRuleActionComponent(type = PhecdaAction.TypeEnum.ALARM)
public class AlarmActionImpl implements PhecdaRuleAction {
    private final StringRedisTemplate stringRedisTemplate;
    private final AlarmService alarmService;

    @Override
    public void execute(ActionArgs actionArgs, PhecdaAction phecdaAction) {
        if (StringUtils.isBlank(actionArgs.getRuleName()) || StringUtils.isBlank(actionArgs.getDeviceName())) {
            return;
        }
        AlarmPhecdaAction alarmAction = (AlarmPhecdaAction) phecdaAction;
        log.info("alarmAction: {}, facts: {}", alarmAction, JsonUtils.toJsonString(actionArgs));

        if (alarmAction.getTriggerMode() == AlarmPhecdaAction.TriggerMode.SINGLE) {
            singleProcess(actionArgs, alarmAction);
        } else if (alarmAction.getTriggerMode() == AlarmPhecdaAction.TriggerMode.CONTINUOUS) {
            continuousProcess(actionArgs, alarmAction);
        }
    }

    /**
     * 持续触发报警
     */
    public void continuousProcess(ActionArgs actionArgs, AlarmPhecdaAction alarmAction) {
        if (Objects.nonNull(alarmAction.getDuration()) && NumberUtil.compare(alarmAction.getDuration(), 0) > 0) { //有持续时间要求
            Object continuousStartTime = stringRedisTemplate.opsForHash().get(RuleUtils.ruleContinuousKey(actionArgs.getRuleName()), RULE_CONTINUOUS_START_PROPERTY_KEY);
            if (Objects.nonNull(continuousStartTime)) {
                Instant continuousStartInstance = ZonedDateTime.parse(continuousStartTime.toString(), DateTimeFormatter.RFC_1123_DATE_TIME).toInstant();
                Duration duration = Duration.between(Instant.now(), continuousStartInstance);
                if (duration.toSeconds() <= alarmAction.getDuration()) {
                    return;
                }
            } else {
                return;
            }
        }
        singleProcess(actionArgs, alarmAction);
    }

    /**
     * 直接报警
     */
    public void singleProcess(ActionArgs actionArgs, AlarmPhecdaAction alarmAction) {
        if (Objects.nonNull(alarmAction.getInterval()) && NumberUtil.compare(alarmAction.getInterval(), 0) > 0) {
            String previousTime = stringRedisTemplate.opsForValue().get(RuleUtils.ruleIntervalKey(actionArgs.getRuleName()));
            if (StrUtil.isNotBlank(previousTime)) {
                Instant previousInstant = ZonedDateTime.parse(previousTime, DateTimeFormatter.RFC_1123_DATE_TIME).toInstant();
                Duration duration = Duration.between(Instant.now(), previousInstant);
                if (duration.toSeconds() < alarmAction.getInterval()) {
                    return;
                }
            }
            stringRedisTemplate.opsForValue().set(RuleUtils.ruleIntervalKey(actionArgs.getRuleName()), Instant.now().toString(), Duration.ofSeconds(alarmAction.getInterval()));
        }
        alarm(actionArgs, alarmAction);

    }


    public void alarm(ActionArgs actionArgs, AlarmPhecdaAction alarmAction) {
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
