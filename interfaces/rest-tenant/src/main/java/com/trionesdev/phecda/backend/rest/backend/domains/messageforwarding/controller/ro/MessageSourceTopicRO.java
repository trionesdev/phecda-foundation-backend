package com.trionesdev.phecda.backend.rest.backend.domains.messageforwarding.controller.ro;

import lombok.Data;
import com.trionesdev.phecda.backend.core.domains.messageforwarding.internal.model.source.SourceProps;

public class MessageSourceTopicRO {
    @Data
    public static class Create {
        private SourceProps properties;
    }
}
