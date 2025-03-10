package com.trionesdev.phecda.foundation.rest.tenant.domains.messageforwarding.controller.ro;

import com.trionesdev.phecda.foundation.core.domains.messageforwarding.shared.enums.SinkActionType;
import lombok.Data;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.shared.model.sinkaction.SinkActionProps;

@Data
public class MessageSinkUpdateRO {
    private String name;
    private String description;
    private SinkActionType type;
    private SinkActionProps action;
}
