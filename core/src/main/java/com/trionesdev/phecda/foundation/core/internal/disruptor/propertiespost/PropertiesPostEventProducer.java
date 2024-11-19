package com.trionesdev.phecda.foundation.core.internal.disruptor.propertiespost;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
//@Component
public class PropertiesPostEventProducer {
    private final Disruptor<PropertiesPostEvent> postEventDisruptor;

    public void sender(String topic, PropertiesPostMessage message) {
        RingBuffer<PropertiesPostEvent> ringBuffer = postEventDisruptor.getRingBuffer();
        long sequence = ringBuffer.next();
        try {
            PropertiesPostEvent event = ringBuffer.get(sequence);
            event.setTopic(topic);
            event.setMessage(message);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
