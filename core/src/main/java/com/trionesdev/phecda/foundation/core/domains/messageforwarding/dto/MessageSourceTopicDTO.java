package com.trionesdev.phecda.foundation.core.domains.messageforwarding.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.shared.model.source.SourceProps;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MessageSourceTopicDTO {
    private String id;
    private String sourceId;
    private String topic;
    private SourceProps properties;
    private Product product;

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Product {
        private String productKey;
        private String name;
    }
}
