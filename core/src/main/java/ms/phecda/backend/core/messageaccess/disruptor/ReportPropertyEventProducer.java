package ms.phecda.backend.core.messageaccess.disruptor;

import com.lmax.disruptor.RingBuffer;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.messageaccess.model.ReadPropertyMessage;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReportPropertyEventProducer {
    private final RingBuffer<ReportPropertyEvent> ringBuffer;

    public void sender(ReadPropertyMessage message) {
        long sequence = ringBuffer.next();
        try {
            ReportPropertyEvent event = ringBuffer.get(sequence);
            event.setMessage(message);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
