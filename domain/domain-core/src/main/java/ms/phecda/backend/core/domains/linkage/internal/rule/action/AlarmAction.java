package ms.phecda.backend.core.domains.linkage.internal.rule.action;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlarmAction extends PhecdaAction {
    private String alarmType;
    private String alarmLevel;
    private String description;

    @Override
    public TypeEnum getType() {
        return TypeEnum.ALARM;
    }


}
