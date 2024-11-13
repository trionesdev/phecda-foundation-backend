package com.trionesdev.phecda.backend.core.internal.disruptor.propertiespost;

import lombok.Data;

@Data
public class PropertiesPostEvent {
    private String topic;
    private PropertiesPostMessage message;
}
