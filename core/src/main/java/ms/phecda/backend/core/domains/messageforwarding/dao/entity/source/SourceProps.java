package ms.phecda.backend.core.domains.messageforwarding.dao.entity.source;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
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
                @JsonSubTypes.Type(value = ThingPropertyReportSourceProps.class,name = "THING_PROPERTY_REPORT")
        }
)
public abstract class SourceProps {

    private Type type;
    public enum Type{
        THING_PROPERTY_REPORT
    }

    public abstract String generateTopic();
}
