package com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.enums.SinkActionType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageSinkPO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.model.sinkaction.SinkAction;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.factory.action.ForwardingActionComponent;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dto.MessageForwardingRuleDTO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dto.MessageSourceDTO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.manager.impl.MessageForwardingRuleManager;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.factory.action.AbsForwardingAction;
import com.trionesdev.phecda.foundation.core.internal.util.MqttTopicUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class ForwardingActionFactory {
    private final List<MessageForwardingRuleDTO> messageForwardingRules = new ArrayList<>();
    private final Map<SinkActionType, AbsForwardingAction> forwardingActionMap = new HashMap<>();

    private final List<AbsForwardingAction> actions;

    private final MessageForwardingRuleManager messageForwardingRuleManager;

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

    public void syncMessageForwardingRules(){
        log.info("[ForwardingActionFactory#syncMessageForwardingRules] message forwarding rules update");
        messageForwardingRules.addAll(messageForwardingRuleManager.activeForwardingList());
    }

    /**
     * 消息转发
     *
     * @param message
     */
    public void messageForwarding(String topic, byte[] message) {
        for (MessageForwardingRuleDTO forwarding : messageForwardingRules) {
            if (Objects.isNull(forwarding.getSource()) || CollectionUtil.isEmpty(forwarding.getSource().getTopics()) || CollectionUtil.isEmpty(forwarding.getSinks())) {
                continue;
            }
            for (MessageSourceDTO.Topic topicItem : forwarding.getSource().getTopics()) {
                if (StrUtil.isNotBlank(topicItem.getTopic()) && MqttTopicUtils.isMatched(topicItem.getTopic(), topic)) {
                    for (MessageSinkPO sink : forwarding.getSinks()) {
                        sink.getAction().setId(sink.getId());
                        write(sink.getAction(), message);
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
