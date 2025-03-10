package com.trionesdev.phecda.foundation.core.domains.messageforwarding.shared.model.source;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.trionesdev.phecda.foundation.core.internal.util.TopicUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Data
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes(
        {
                @JsonSubTypes.Type(value = ThingPropertyReportSourceProps.class, name = "THING_PROPERTY_REPORT"),
                @JsonSubTypes.Type(value = ThingEventReportSourceProps.class, name = "THING_EVENT_REPORT"),
        }
)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class SourceProps {

    private MessageType type;

    public abstract String generateTopic();

    @Getter
    @AllArgsConstructor
    public enum TopicTemplate {
        THING_PROPERTY_POST(TopicUtils.propertyPostTopic("${productKey}", "${deviceName}")),
        THING_EVENT_POST(TopicUtils.eventPostTopic("${productKey}", "${deviceName}"));
        private final String template;
    }

}
