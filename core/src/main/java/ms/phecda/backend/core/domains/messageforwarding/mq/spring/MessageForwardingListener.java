package ms.phecda.backend.core.domains.messageforwarding.mq.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MessageForwardingListener {
//    private final KafkaTemplate<String,String> kafkaTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    @EventListener(MessageForwardingRuleChangeEvent.class)
    public void ruleChange(MessageForwardingRuleChangeEvent event){
        stringRedisTemplate.convertAndSend("message-forwarding-rule-change",event.getRule().getId());
//        kafkaTemplate.send("message-forwarding-rule-change",event.getRule().getId());
    }

    @EventListener(MessageSinkChangeEvent.class)
    public void sinkActionChange(MessageSinkChangeEvent event){
        stringRedisTemplate.convertAndSend("message-sink-change",event.getMessageSink().getId());
//        kafkaTemplate.send("message-sink-change", event.getMessageSink().getId());
    }

}
