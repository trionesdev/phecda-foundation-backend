package com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.model.source;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import com.trionesdev.phecda.foundation.core.internal.util.TopicUtils;

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

    private Type type;

    public enum Type {
        THING_PROPERTY_REPORT,
        THING_EVENT_REPORT
    }

    public abstract String generateTopic();

    @Getter
    @AllArgsConstructor
    public enum TopicTemplate {
        THING_PROPERTY_POST(TopicUtils.propertyPostTopic("${productKey}", "${deviceName}"));
        private final String template;
    }

}
