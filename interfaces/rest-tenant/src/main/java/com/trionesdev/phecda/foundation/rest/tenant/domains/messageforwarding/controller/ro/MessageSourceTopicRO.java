package com.trionesdev.phecda.foundation.rest.tenant.domains.messageforwarding.controller.ro;

import lombok.Data;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.shared.model.source.SourceProps;

public class MessageSourceTopicRO {
    @Data
    public static class Create {
        private SourceProps properties;
    }
}
