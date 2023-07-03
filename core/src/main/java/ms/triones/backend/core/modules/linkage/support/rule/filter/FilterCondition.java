package ms.triones.backend.core.modules.linkage.support.rule.filter;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.triones.backend.core.modules.linkage.support.rule.RuleCondition;
import ms.triones.backend.core.modules.linkage.support.rule.othercondition.ThingModelPropertyCondition;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
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
                @JsonSubTypes.Type(value = ThingModelPropertyCondition.class, name = "THING_MODEL_PROPERTY_EXPORT"),
        }
)
public abstract class FilterCondition extends RuleCondition {
    private TypeEnum type;

    public enum TypeEnum{
        THING_MODEL_PROPERTY_EXPORT
    }
}
