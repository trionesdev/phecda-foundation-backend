package com.trionesdev.phecda.foundation.core.domains.linkage.service.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.aggregate.entity.LinkageScene;
import com.trionesdev.phecda.foundation.core.domains.linkage.service.factory.ruleaction.PhecdaRuleActionHandler;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.LinkageProperties;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.RuleUtils;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.ActionArgs;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.ActionTrigger;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.ActionTrigger.TriggerMode;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.PhecdaAction;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.PhecdaRuleActionComponent;
import com.trionesdev.phecda.foundation.core.domains.device.provider.impl.DeviceProvider;
import org.jeasy.rules.api.Facts;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.RuleConstants.*;

@RequiredArgsConstructor
@Component
public class RuleActionFactory {
    private final LinkageProperties linkageProperties;
    private final StringRedisTemplate stringRedisTemplate;
    private final DeviceProvider deviceProvider;
    private final Map<PhecdaAction.TypeEnum, PhecdaRuleActionHandler> actionMap = new HashMap<>();

    private final List<PhecdaRuleActionHandler> handlers;

    @PostConstruct
    public void init() {
        if (CollectionUtil.isNotEmpty(handlers)) {
            handlers.forEach(action -> {
                PhecdaRuleActionComponent component = AnnotationUtils.getAnnotation(action.getClass(), PhecdaRuleActionComponent.class);
                if (Objects.nonNull(component)) {
                    actionMap.put(component.type(), action);
                }
            });
        }
    }

    public PhecdaRuleActionHandler getAction(PhecdaAction.TypeEnum type) {
        return actionMap.get(type);
    }

    public Boolean canActionTrigger(LinkageScene linkageScene) {
        ActionTrigger actionTrigger = linkageScene.getActionTrigger();
        if (actionTrigger.getTriggerMode() == TriggerMode.SINGLE) {
            return meetActionInterval(linkageScene);
        } else if (actionTrigger.getTriggerMode() == TriggerMode.CONTINUOUS) {
            if (Objects.nonNull(actionTrigger.getDuration()) && NumberUtil.compare(actionTrigger.getDuration(), 0) > 0) { //有持续时间要求
                Object continuousStartTime = stringRedisTemplate.opsForHash().get(RuleUtils.ruleContinuousKey(linkageScene.getId()), RULE_CONTINUOUS_START_PROPERTY_KEY);
                if (Objects.nonNull(continuousStartTime)) {
                    Instant continuousStartInstance = ZonedDateTime.parse(continuousStartTime.toString(), DateTimeFormatter.RFC_1123_DATE_TIME).toInstant();
                    Duration duration = Duration.between(Instant.now(), continuousStartInstance);
                    if (duration.toSeconds() <= actionTrigger.getDuration()) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return meetActionInterval(linkageScene);
        } else {
            return true;
        }
    }

    /**
     * 是否满足时间间隔
     *
     * @param linkageScene
     * @return
     */
    public boolean meetActionInterval(LinkageScene linkageScene) {
        long actionInterval = Optional.of(linkageScene.getActionTrigger()).map(ActionTrigger::getInterval).orElse(linkageProperties.getDefaultActionInterval());
        if (NumberUtil.compare(actionInterval, 0) > 0) {
            String previousTime = stringRedisTemplate.opsForValue().get(RuleUtils.ruleIntervalKey(linkageScene.getId()));
            if (StrUtil.isNotBlank(previousTime)) {
                Instant previousInstant = ZonedDateTime.parse(previousTime, DateTimeFormatter.RFC_1123_DATE_TIME).toInstant();
                Duration duration = Duration.between(Instant.now(), previousInstant);
                if (duration.toSeconds() < actionInterval) {
                    return false;
                }
            }
            stringRedisTemplate.opsForValue().set(RuleUtils.ruleIntervalKey(linkageScene.getId()), Instant.now().toString(), Duration.ofSeconds(actionInterval));
        }
        return true;
    }

    public ActionArgs factsToActionArgs(Facts facts) {
        Map<String, ActionArgs.Reading> readingMap = facts.get(FACT_READINGS);
        if (MapUtil.isNotEmpty(readingMap)) {
            readingMap.forEach((k, v) -> {
                var property = deviceProvider.findThingModelPropertyByKey(facts.get(FACT_PRODUCT_KEY), v.getIdentifier());
                if (Objects.nonNull(property)) {
                    v.setLabel(property.getName());
                }
            });
        }
        return ActionArgs.builder()
                .ruleName(facts.get(FACT_RULE_NAME))
                .productId(facts.get(FACT_PRODUCT_ID))
                .productKey(facts.get(FACT_PRODUCT_KEY))
                .deviceId(facts.get(FACT_DEVICE_ID))
                .deviceName(facts.get(FACT_DEVICE_NAME))
                .readings(readingMap)
                .build();
    }
}
