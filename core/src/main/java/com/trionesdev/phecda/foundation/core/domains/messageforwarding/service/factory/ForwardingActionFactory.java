package com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.factory;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSON;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.root.MessageForwardingRuleAggregate;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.enums.SinkActionType;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.model.sinkaction.SinkAction;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.manager.impl.MessageForwardingRuleManager;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.factory.action.AbsForwardingAction;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.factory.action.ForwardingActionComponent;
import com.trionesdev.phecda.foundation.core.internal.disruptor.propertiespost.PropertiesPostMessage;
import com.trionesdev.phecda.infrastructure.rule.PhecdaRule;
import com.trionesdev.phecda.infrastructure.rule.PhecdaRuleEngine;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.model.sinkaction.SinkActionProps;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.factory.action.ForwardingActionComponent;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.manager.impl.MessageForwardingRuleManager;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.factory.action.AbsForwardingAction;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class ForwardingActionFactory {
    /**
     * 消息转发规则集合
     */
    private final Map<SinkActionType, AbsForwardingAction> forwardingActionMap = new HashMap<>();

    private final List<AbsForwardingAction> actions;

    private final MessageForwardingRuleManager messageForwardingRuleManager;
    private final PhecdaRuleEngine rulesEngine = new PhecdaRuleEngine();
    private final Rules sourceRules = new Rules();


    /**
     * 初始化的时候，加载所有的Action以及转发规则
     */
    @PostConstruct
    public void init() {
        if (CollectionUtil.isNotEmpty(actions)) {
            actions.forEach(forwardingAction -> {
                ForwardingActionComponent actionComponent = AnnotationUtils.getAnnotation(forwardingAction.getClass(), ForwardingActionComponent.class);
                if (Objects.nonNull(actionComponent)) {
                    forwardingActionMap.put(actionComponent.type(), forwardingAction);
                }
            });
        }
        syncMessageForwardingRules();
    }

    /**
     * 同步消息转发规则
     */
    public void syncMessageForwardingRules() {
        log.info("[ForwardingActionFactory#syncMessageForwardingRules] sync message forwarding rules");
        var forwardingRules = messageForwardingRuleManager.activeForwardingList();
        if (CollectionUtils.isEmpty(forwardingRules)) {
            forwardingRules.forEach(forwardingRule -> {
                registerRule(forwardingRule);
            });
        }
    }


    public void registerRule(MessageForwardingRuleAggregate rule) {
        sourceRules.register(createRule(rule));
    }

    public void unregisterRule(String id) {
        sourceRules.unregister(id);
    }

    public void fireForwardRule(Facts facts, PropertiesPostMessage message) {
        rulesEngine.fire(sourceRules, facts, message);
    }


    public void write(SinkActionProps sinkAction, PropertiesPostMessage content) {
        AbsForwardingAction forwardingAction = forwardingActionMap.get(sinkAction.getType());
        if (Objects.nonNull(forwardingAction)) {
            forwardingAction.write(sinkAction, JSON.toJSONBytes(content));
        }
    }

    public Rule createRule(MessageForwardingRuleAggregate rule) {
        if (Objects.isNull(rule.getSource()) || CollectionUtils.isEmpty(rule.getSource().getRules())) {
            return null;
        }
        if (CollectionUtils.isEmpty(rule.getSinks())) {
            return null;
        }

        return new PhecdaRule<PropertiesPostMessage>().name(rule.getId()).when(rule.conditionEl()).then((facts, content) -> {
            for (MessageSink sink : rule.getSinks()) {
                sink.getAction().setId(sink.getId());
                write(sink.getAction(), content);
                break;
            }
        });
    }
}
