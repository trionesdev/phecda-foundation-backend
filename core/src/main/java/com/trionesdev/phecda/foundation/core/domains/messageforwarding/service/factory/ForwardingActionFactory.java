package com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.entity.MessageSink;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.root.MessageForwardingRuleAggregate;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.enums.SinkActionType;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.model.MessageForwardingCmd;
import com.trionesdev.phecda.foundation.core.internal.disruptor.propertiespost.PropertiesPostMessage;
import com.trionesdev.phecda.infrastructure.rule.PhecdaRuleEngine;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.model.sinkaction.SinkAction;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.factory.action.ForwardingActionComponent;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.entity.MessageSource;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.manager.impl.MessageForwardingRuleManager;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.factory.action.AbsForwardingAction;
import com.trionesdev.phecda.foundation.core.internal.util.MqttTopicUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
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
    private List<MessageForwardingRuleAggregate> messageForwardingRules = new ArrayList<>();
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
        messageForwardingRules = messageForwardingRuleManager.activeForwardingList();
    }

    /**
     * 消息转发
     * 通过对mqtt的topic进行匹配，匹配成功后，通过sink的action进行转发
     *
     * @param message
     */
//    public void messageForwarding(String topic, byte[] message) {
//        if (CollectionUtils.isEmpty(messageForwardingRules)) {
//            return;
//        }
//        for (MessageForwardingRuleAggregate forwarding : messageForwardingRules) {
//            if (Objects.isNull(forwarding.getSource()) || CollectionUtil.isEmpty(forwarding.getSource().getTopics()) || CollectionUtil.isEmpty(forwarding.getSinks())) {
//                continue;
//            }
//            for (MessageSource.Topic topicItem : forwarding.getSource().getTopics()) {
//                if (StrUtil.isNotBlank(topicItem.getTopic()) && MqttTopicUtils.isMatched(topicItem.getTopic(), topic)) {
//                    for (MessageSink sink : forwarding.getSinks()) {
//                        sink.getAction().setId(sink.getId());
//                        write(sink.getAction(), message);
//                        break;
//                    }
//                }
//            }
//        }
//
//    }

    public void fireForwardRule(Facts facts, PropertiesPostMessage message) {
        rulesEngine.fire(sourceRules, facts, message);
    }


    public void write(SinkAction sinkAction, PropertiesPostMessage content) {
        AbsForwardingAction forwardingAction = forwardingActionMap.get(sinkAction.getType());
        if (Objects.nonNull(forwardingAction)) {
            forwardingAction.write(sinkAction, JSON.toJSONBytes(content));
        }
    }

}
