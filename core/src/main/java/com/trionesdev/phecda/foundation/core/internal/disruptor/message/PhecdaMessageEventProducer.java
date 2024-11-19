package com.trionesdev.phecda.foundation.core.internal.disruptor.message;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.trionesdev.phecda.foundation.core.internal.disruptor.MessageType;
import com.trionesdev.phecda.model.device.PhecdaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PhecdaMessageEventProducer {
    private final Disruptor<PhecdaMessageEvent> messsageEventDisruptor;

    public void sender(String topic, PhecdaMessage message) {
        sender(null, topic, message);
    }

    public void sender(MessageType type, String topic, PhecdaMessage message) {
        RingBuffer<PhecdaMessageEvent> ringBuffer = messsageEventDisruptor.getRingBuffer();
        long sequence = ringBuffer.next();
        try {
            PhecdaMessageEvent event = ringBuffer.get(sequence);
            event.setTopic(topic);
            event.setMessage(message);
            event.setType(type);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
