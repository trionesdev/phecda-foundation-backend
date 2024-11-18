package com.trionesdev.phecda.foundation.core.internal.disruptor.message;

import lombok.Data;

@Data
public class PhecdaMessageEvent {
    private String topic;
    private byte[] message;
}
