package com.trionesdev.phecda.backend.core.domains.linkage.internal.rule.filter;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.trionesdev.phecda.backend.core.domains.linkage.internal.rule.RuleCondition;

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
                @JsonSubTypes.Type(value = ThingModelExportCondition.class, name = "THING_MODEL_PROPERTY_EXPORT"),
        }
)
public abstract class FilterCondition extends RuleCondition {
    private TypeEnum type;

    public enum TypeEnum{
        THING_MODEL_PROPERTY_EXPORT
    }
}
