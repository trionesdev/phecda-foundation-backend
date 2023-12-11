package ms.phecda.backend.core.domains.messageforwarding.mq.redis;

import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.messageforwarding.service.factory.action.KafkaForwardingAction;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SinkActionChangeListener implements MessageListener {
    private final KafkaForwardingAction kafkaForwardingAction;
    @Override
    public void onMessage(Message message, byte[] pattern) {
        kafkaForwardingAction.kafkaSync();
    }
}
