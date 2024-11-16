package com.trionesdev.phecda.foundation.core.domains.linkage.service.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.trionesdev.phecda.foundation.core.domains.linkage.dao.criteria.LinkageSceneCriteria;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.aggregate.entity.LinkageScene;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.util.LinkageSceneUtils;
import com.trionesdev.phecda.foundation.core.domains.linkage.manager.impl.LinkageSceneManager;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
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
import java.util.concurrent.ConcurrentHashMap;

import static com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.RuleConstants.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class RuleActionFactory {
    //持续场景
    private static final Map<String, Boolean> continuousMap = new ConcurrentHashMap<>();
    private final LinkageProperties linkageProperties;
    private final StringRedisTemplate stringRedisTemplate;
    private final LinkageSceneManager linkageSceneManager;
    private final DeviceProvider deviceProvider;
    private final Map<PhecdaAction.TypeEnum, PhecdaRuleActionHandler> actionMap = new HashMap<>();

    private final List<PhecdaRuleActionHandler> handlers;
    private final DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
    private final Rules linkageRules = new Rules();


    {
        rulesEngine.registerRuleListener(new RuleListener() {
            @Override
            public void beforeExecute(Rule rule, Facts facts) {
                facts.put(FACT_RULE_NAME, rule.getName());
            }

            @Override
            public void afterEvaluate(Rule rule, Facts facts, boolean evaluationResult) {
                if (BooleanUtils.isTrue(continuousMap.get(rule.getName()))) {
                    //region 触发记录，如果是持续触发，则记录触发次数，否则，清空记录
                    if (evaluationResult) {
                        if (stringRedisTemplate.opsForHash().hasKey(RuleUtils.ruleContinuousKey(rule.getName()), RULE_CONTINUOUS_START_PROPERTY_KEY)) {
                            stringRedisTemplate.opsForHash().put(RuleUtils.ruleContinuousKey(rule.getName()), RULE_CONTINUOUS_START_PROPERTY_KEY, Instant.now().toString());
                        }
                        stringRedisTemplate.opsForHash().put(RuleUtils.ruleContinuousKey(rule.getName()), RULE_CONTINUOUS_COUNT_PROPERTY_KEY, 1);
                    } else {
                        stringRedisTemplate.opsForHash().delete(RuleUtils.ruleContinuousKey(rule.getName()));
                    }
                    //endregion
                }
            }
        });
    }


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
        registerAllRules();
    }

    /**
     * 注册所有规则
     */
    public void registerAllRules() {
        List<LinkageScene> scenes = linkageSceneManager.queryList(LinkageSceneCriteria.builder().enabled(true).build());
        if (CollectionUtils.isNotEmpty(scenes)) {
            scenes.forEach(this::registerRule);
        }
    }

    public void registerRule(LinkageScene linkageScene) {
        Rule rule = LinkageSceneUtils.createRule(linkageScene, this);
        if (Objects.nonNull(rule)) {
            if (LinkageSceneUtils.continuous(linkageScene)) {
                continuousMap.put(rule.getName(), true);
            }
            linkageRules.register(rule);
        }
    }

    public void unregisterRule(String id) {
        linkageRules.unregister(id);
        continuousMap.remove(id);
    }


    /**
     * 触发规则
     *
     * @param facts
     */
    public void fireRules(Facts facts) {
        if (linkageRules.isEmpty()) {
            if (log.isInfoEnabled()) {
                log.info("[LinkageSceneService] no rules");
            }
            return;
        }
        rulesEngine.fire(linkageRules, facts);
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
