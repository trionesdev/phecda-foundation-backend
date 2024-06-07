package ms.phecda.backend.rest.backend.domains.messageforwarding.controller.ro;

import lombok.Data;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.source.SourceProps;

public class MessageSourceTopicRO {
    @Data
    public static class Create {
        private SourceProps properties;
    }
}
