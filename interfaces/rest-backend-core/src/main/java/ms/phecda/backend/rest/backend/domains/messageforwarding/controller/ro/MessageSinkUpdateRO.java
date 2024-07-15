package ms.phecda.backend.rest.backend.domains.messageforwarding.controller.ro;

import lombok.Data;
import ms.phecda.backend.core.domains.messageforwarding.dao.po.sinkaction.SinkAction;

@Data
public class MessageSinkUpdateRO {
    private String name;
    private String description;
    private SinkAction.TypeEnum type;
    private SinkAction action;
}
