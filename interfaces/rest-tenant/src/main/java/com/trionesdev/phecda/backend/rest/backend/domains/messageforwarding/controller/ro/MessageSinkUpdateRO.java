package com.trionesdev.phecda.backend.rest.backend.domains.messageforwarding.controller.ro;

import com.trionesdev.phecda.backend.core.domains.messageforwarding.internal.enums.SinkActionType;
import lombok.Data;
import com.trionesdev.phecda.backend.core.domains.messageforwarding.internal.model.sinkaction.SinkAction;

@Data
public class MessageSinkUpdateRO {
    private String name;
    private String description;
    private SinkActionType type;
    private SinkAction action;
}
