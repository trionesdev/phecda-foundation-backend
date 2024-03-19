package ms.phecda.backend.core.domains.messageforwarding.mq.redis;

import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.messageforwarding.service.factory.action.KafkaForwardingAction;
import ms.phecda.backend.core.domains.messageforwarding.service.impl.MessageSinkService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SinkActionChangeListener implements MessageListener {
    private final MessageSinkService messageSinkService;
    private final KafkaForwardingAction kafkaForwardingAction;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String id = new String(message.getBody());
        messageSinkService.findById(id).ifPresent(record -> {
            switch (record.getAction().getType()) {
                case KAFKA:
                    kafkaForwardingAction.kafkaSync();
                    break;
            }
        });

    }
}
