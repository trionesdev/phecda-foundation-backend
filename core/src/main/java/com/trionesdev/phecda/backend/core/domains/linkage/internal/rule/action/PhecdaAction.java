package com.trionesdev.phecda.backend.core.domains.linkage.internal.rule.action;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


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
                @JsonSubTypes.Type(value = AlarmAction.class, name = "ALARM"),
                @JsonSubTypes.Type(value = NotificationAction.class, name = "NOTIFICATION"),
                @JsonSubTypes.Type(value = ServiceInvocationAction.class, name = "SERVICE_INVOCATION"),
        }
)
public abstract class PhecdaAction {
    private TypeEnum type;

    public enum TypeEnum {
        ALARM,
        NOTIFICATION,
        SERVICE_INVOCATION
    }
}
