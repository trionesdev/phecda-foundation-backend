package com.trionesdev.phecda.foundation.core.internal.disruptor.message;

import com.trionesdev.phecda.foundation.core.internal.disruptor.MessageType;
import com.trionesdev.phecda.model.device.PhecdaMessage;
import lombok.Data;

@Data
public class PhecdaMessageEvent {
    private String topic;
    private MessageType type;
    private PhecdaMessage message;
}
