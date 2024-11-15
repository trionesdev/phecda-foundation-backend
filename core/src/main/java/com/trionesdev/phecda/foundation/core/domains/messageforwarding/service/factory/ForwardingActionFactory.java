package com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.entity.MessageSink;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.root.MessageForwardingRuleAggregate;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.enums.SinkActionType;
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
    public void messageForwarding(String topic, byte[] message) {
        if (CollectionUtils.isEmpty(messageForwardingRules)) {
            return;
        }
        for (MessageForwardingRuleAggregate forwarding : messageForwardingRules) {
            if (Objects.isNull(forwarding.getSource()) || CollectionUtil.isEmpty(forwarding.getSource().getTopics()) || CollectionUtil.isEmpty(forwarding.getSinks())) {
                continue;
            }
            for (MessageSource.Topic topicItem : forwarding.getSource().getTopics()) {
                if (StrUtil.isNotBlank(topicItem.getTopic()) && MqttTopicUtils.isMatched(topicItem.getTopic(), topic)) {
                    for (MessageSink sink : forwarding.getSinks()) {
                        sink.getAction().setId(sink.getId());
                        write(sink.getAction(), message);
                        break;
                    }
                }
            }
        }

    }


    public void write(SinkAction sinkAction, byte[] content) {
        AbsForwardingAction forwardingAction = forwardingActionMap.get(sinkAction.getType());
        if (Objects.nonNull(forwardingAction)) {
            forwardingAction.write(sinkAction, content);
        }
    }

}
