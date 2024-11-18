package com.trionesdev.phecda.foundation.core.internal.disruptor.message;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PhecdaMessageEventProducer {
    private final Disruptor<PhecdaMessageEvent> messsageEventDisruptor;

    public void sender(String topic, byte[] message) {
        RingBuffer<PhecdaMessageEvent> ringBuffer = messsageEventDisruptor.getRingBuffer();
        long sequence = ringBuffer.next();
        try {
            PhecdaMessageEvent event = ringBuffer.get(sequence);
            event.setTopic(topic);
            event.setMessage(message);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
