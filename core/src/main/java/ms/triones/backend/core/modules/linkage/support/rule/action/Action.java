package ms.triones.backend.core.modules.linkage.support.rule.action;


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
        }
)
public abstract class Action {
    private TypeEnum type;

    public enum TypeEnum {
        ALARM,
        NOTIFICATION
    }
}
