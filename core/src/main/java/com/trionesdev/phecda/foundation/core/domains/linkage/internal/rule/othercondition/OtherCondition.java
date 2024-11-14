package com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.othercondition;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.RuleCondition;

@Deprecated
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
                @JsonSubTypes.Type(value = ThingModelPropertyCondition.class, name = "THING_MODEL_PROPERTY"),
        }
)
public abstract class OtherCondition extends RuleCondition {
    private TypeEnum type;

    public enum TypeEnum {
        THING_MODEL_PROPERTY
    }
}
