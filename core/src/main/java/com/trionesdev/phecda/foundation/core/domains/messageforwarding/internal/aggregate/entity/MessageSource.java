package com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.aggregate.entity;

import com.trionesdev.phecda.foundation.core.domains.messageforwarding.shared.model.source.SourceProps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MessageSource {
    private String id;
    private List<Topic> topics;

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Topic {
        private String id;
        private String topic;
        private SourceProps properties;
        public String getTopic() {
            return properties.generateTopic();
        }
    }

}
