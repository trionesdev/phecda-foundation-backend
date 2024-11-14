package com.trionesdev.phecda.foundation.rest.tenant.domains.messageforwarding.controller.ro;

import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.enums.SinkActionType;
import lombok.Data;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.model.sinkaction.SinkAction;

@Data
public class MessageSinkCreateRO {
    private String name;
    private String description;
    private SinkActionType type;
    private SinkAction action;
}
