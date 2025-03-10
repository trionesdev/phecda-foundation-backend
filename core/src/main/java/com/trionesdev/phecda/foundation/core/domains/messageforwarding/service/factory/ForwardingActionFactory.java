package com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.factory;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSON;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.entity.MessageSink;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.entity.MessageSource;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.root.MessageForwardingRuleAggregate;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.shared.enums.SinkActionType;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.shared.model.sinkaction.SinkActionProps;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.manager.impl.MessageForwardingRuleManager;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.factory.action.AbsForwardingAction;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.factory.action.ForwardingActionComponent;
import com.trionesdev.phecda.foundation.core.internal.util.MqttTopicUtils;
import com.trionesdev.phecda.foundation.core.internal.util.TopicUtils;
import com.trionesdev.phecda.infrastructure.configuration.mqtt.PhecdaMqttProperties;
import com.trionesdev.phecda.model.device.PhecdaMessage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class ForwardingActionFactory {
    private final PhecdaMqttProperties mqttProperties;
    private List<MessageForwardingRuleAggregate> forwardingRules = new ArrayList<>();
    /**
     * 消息转发规则集合
     */
    private final Map<SinkActionType, AbsForwardingAction> forwardingActionMap = new HashMap<>();
    private final List<AbsForwardingAction> actions;
    private final MessageForwardingRuleManager messageForwardingRuleManager;


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
        forwardingRules = messageForwardingRuleManager.activeForwardingList();
    }

    public void write(SinkActionProps sinkAction, PhecdaMessage content) {
        AbsForwardingAction forwardingAction = forwardingActionMap.get(sinkAction.getType());
        if (Objects.nonNull(forwardingAction)) {
            forwardingAction.write(sinkAction, JSON.toJSONBytes(content));
        }
    }


    public void fireForwardRule(String topic, PhecdaMessage message) {
        for (MessageForwardingRuleAggregate rule : forwardingRules) {
            if (CollectionUtils.isEmpty(rule.getSinks()) || CollectionUtils.isEmpty(rule.getSinks())) {
                continue;
            }
            for (MessageSource.Topic sourceTopic : rule.getSource().getTopics()) {
                if (MqttTopicUtils.isMatched(TopicUtils.join(mqttProperties.getTopicPrefix(),sourceTopic.getTopic()), topic)) {
                    for (MessageSink sink : rule.getSinks()) {
                        write(sink.getAction(), message);
                        break;
                    }
                }
            }
        }
    }
}
