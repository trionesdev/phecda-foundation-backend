package com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.entity;

import com.trionesdev.phecda.foundation.core.domains.messageforwarding.shared.enums.SinkActionType;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.shared.model.sinkaction.SinkActionProps;
import lombok.Data;

@Data
public class MessageSink {
    private String id;
    private String name;
    private String description;
    private SinkActionType type;
    private SinkActionProps action;
    private String state;
}
