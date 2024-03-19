package ms.phecda.backend.core.domains.messageforwarding.mq.spring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import static ms.phecda.backend.core.domains.messageforwarding.mq.MqConstants.MESSAGE_FORWARDING_RULE_CHANGE;
import static ms.phecda.backend.core.domains.messageforwarding.mq.MqConstants.MESSAGE_SINK_CHANGE;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageForwardingListener {
    //    private final KafkaTemplate<String,String> kafkaTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    @EventListener(MessageForwardingRuleChangeEvent.class)
    public void ruleChange(MessageForwardingRuleChangeEvent event) {
        stringRedisTemplate.convertAndSend(MESSAGE_FORWARDING_RULE_CHANGE, event.getRule().getId());
        log.info("[MessageForwardingListener#ruleChange] rule change {}", event.getRule().getName());
//        kafkaTemplate.send("message-forwarding-rule-change",event.getRule().getId());
    }

    @EventListener(MessageSinkChangeEvent.class)
    public void sinkActionChange(MessageSinkChangeEvent event) {
        stringRedisTemplate.convertAndSend(MESSAGE_SINK_CHANGE, event.getMessageSink().getId());
        log.info("[MessageForwardingListener#sinkActionChange] sink action change {}", event.getMessageSink().getName());
//        kafkaTemplate.send("message-sink-change", event.getMessageSink().getId());
    }

}
