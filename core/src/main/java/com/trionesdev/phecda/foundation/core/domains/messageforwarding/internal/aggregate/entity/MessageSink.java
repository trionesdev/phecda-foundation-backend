package com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.entity;

import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.enums.SinkActionType;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.model.sinkaction.SinkAction;
import lombok.Data;

@Data
public class MessageSink {
    private String id;
    private String name;
    private String description;
    private SinkActionType type;
    private SinkAction action;
    private String state;
}
