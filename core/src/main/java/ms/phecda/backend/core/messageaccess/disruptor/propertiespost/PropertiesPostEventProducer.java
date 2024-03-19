package ms.phecda.backend.core.messageaccess.disruptor.propertiespost;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.messageaccess.mqtt.model.MqttPropertiesPostMessage;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PropertiesPostEventProducer {
    private final Disruptor<PropertiesPostEvent> postEventDisruptor;

    public void sender(PropertiesPostMessage message) {
        RingBuffer<PropertiesPostEvent> ringBuffer = postEventDisruptor.getRingBuffer();
        long sequence = ringBuffer.next();
        try {
            PropertiesPostEvent event = ringBuffer.get(sequence);
            event.setMessage(message);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
