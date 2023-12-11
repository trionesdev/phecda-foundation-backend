package ms.phecda.backend.core.domains.messageforwarding.service.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageSink;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.sinkaction.SinkAction;
import ms.phecda.backend.core.domains.messageforwarding.manager.dto.MessageForwardingRuleDTO;
import ms.phecda.backend.core.domains.messageforwarding.manager.dto.MessageSourceDTO;
import ms.phecda.backend.core.domains.messageforwarding.manager.impl.MessageForwardingRuleManager;
import ms.phecda.backend.core.domains.messageforwarding.service.factory.action.AbsForwardingAction;
import ms.phecda.backend.core.domains.messageforwarding.service.factory.action.ForwardingActionComponent;
import ms.phecda.backend.core.support.util.MqttTopicUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class ForwardingActionFactory {
    private final List<MessageForwardingRuleDTO> messageForwardingRules = new ArrayList<>();
    private final Map<SinkAction.TypeEnum, AbsForwardingAction> forwardingActionMap = new HashMap<>();

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
        log.info("[ForwardingActionFactory] message forwarding rules update");
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
                    for (MessageSink sink : forwarding.getSinks()) {
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
