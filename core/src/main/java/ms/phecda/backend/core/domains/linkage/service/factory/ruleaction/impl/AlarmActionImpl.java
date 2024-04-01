package ms.phecda.backend.core.domains.linkage.service.factory.ruleaction.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.NumberUtil;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.domains.alarm.dao.entity.Alarm;
import ms.phecda.backend.core.domains.alarm.service.bo.AlarmCreateArgBO;
import ms.phecda.backend.core.domains.alarm.service.impl.AlarmService;
import ms.phecda.backend.core.domains.linkage.service.factory.ruleaction.PhecdaRuleAction;
import ms.phecda.backend.core.domains.linkage.service.impl.LinkageSceneService;
import ms.phecda.backend.core.domains.alarm.dao.entity.AlarmLog;
import ms.phecda.backend.core.domains.alarm.dao.entity.enums.AlarmLevelEnum;
import ms.phecda.backend.core.domains.alarm.dao.entity.enums.DealStatuEnums;
import ms.phecda.backend.core.domains.alarm.service.impl.AlarmLogService;
import ms.phecda.backend.core.domains.linkage.dao.entity.LinkageScene;
import ms.phecda.backend.core.domains.linkage.support.rule.action.Action;
import ms.phecda.backend.core.domains.linkage.support.rule.action.AlarmAction;
import ms.phecda.backend.core.domains.linkage.support.rule.action.PhecdaRuleActionComponent;
import org.apache.commons.lang3.StringUtils;
import org.jeasy.rules.api.Facts;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@PhecdaRuleActionComponent(type = Action.TypeEnum.ALARM)
public class AlarmActionImpl implements PhecdaRuleAction {
    private final AlarmLogService alarmLogService;
    private final AlarmService alarmService;

    @Lazy
    @Resource
    private LinkageSceneService linkageSceneService;

    //同一个设备，同一个规则，上一次告警的时间，暂时先默认静默240分钟告警一次
    private Map<String, Long> lastAlarmTimeMap = Maps.newHashMap();

    @Override
    public void execute(Facts facts, Action action) {
        AlarmAction alarmAction = (AlarmAction) action;
        log.info("alarmAction: {}, facts: {}", alarmAction, facts);

        if (alarmAction.getTriggerMode() == AlarmAction.TriggerMode.SINGLE) {
            if (Objects.nonNull(alarmAction.getInterval()) && NumberUtil.compare(alarmAction.getInterval(), 0) > 0) {

            } else {
                alarm(facts, alarmAction);
            }
        } else if (alarmAction.getTriggerMode() == AlarmAction.TriggerMode.CONTINUOUS) {

        }


//        String deviceName = facts.get("deviceName");
//        String ruleName = facts.get("ruleName");
//        if (StringUtils.isBlank(ruleName) || StringUtils.isBlank(deviceName)) {
//            return;
//        }
//
//        Long lastAlarmTime = lastAlarmTimeMap.get(deviceName + ruleName);
//        if (Objects.nonNull(lastAlarmTime) && System.currentTimeMillis() - lastAlarmTime < 240 * 60 * 1000) {
//            return;
//        } else {
//            lastAlarmTimeMap.put(deviceName + ruleName, System.currentTimeMillis());
//        }
//
//        Optional<LinkageScene> linkageSceneOptional = linkageSceneService.querySceneById(ruleName);
//        linkageSceneOptional.ifPresent(linkageScene -> {
//            AlarmLog alarmLog = AlarmLog.builder()
//                    .title(linkageScene.getName())
//                    .level(AlarmLevelEnum.THIRD_LEVEL)
//                    .alarmTime(Instant.now())
//                    .describe(linkageScene.getDescription())
//                    .dealStatus(DealStatuEnums.PENDING)
//                    .deviceName(deviceName)
////                .assetSn(null)
////                .assetSpareSn(null)
//                    .build();
//
//
//            alarmLogService.create(alarmLog);
//        });
    }

    public void alarm(Facts facts, AlarmAction alarmAction) {
        List<Alarm.Item> eventData = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(facts)) {
            facts.asMap().forEach((k, v) -> {
                if (!CollectionUtil.contains(ListUtil.of("productKey", "deviceName", "ruleName"), k)) {
                    eventData.add(Alarm.Item.builder().identifier(k).value(v).build());
                }
            });
        }
        AlarmCreateArgBO alarm = AlarmCreateArgBO.builder().type(alarmAction.getAlarmType()).level(alarmAction.getAlarmLevel())
                .productKey(facts.get("productKey")).deviceName(facts.get("deviceName")).description(alarmAction.getDescription())
                .eventData(eventData)
                .build();
        alarmService.createAlarm(alarm);
    }

}
