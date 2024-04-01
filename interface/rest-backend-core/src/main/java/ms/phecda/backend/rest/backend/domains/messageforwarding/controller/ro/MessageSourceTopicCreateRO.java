package ms.phecda.backend.rest.backend.domains.messageforwarding.controller.ro;

import lombok.Data;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.source.SourceProps;

@Data
public class MessageSourceTopicCreateRO {
    private SourceProps properties;
}
