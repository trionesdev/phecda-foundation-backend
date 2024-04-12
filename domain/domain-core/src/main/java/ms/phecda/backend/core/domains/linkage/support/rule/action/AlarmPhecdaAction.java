package ms.phecda.backend.core.domains.linkage.support.rule.action;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlarmPhecdaAction extends PhecdaAction {
    private String alarmType;
    private String alarmLevel;
    private TriggerMode triggerMode;
    private Integer duration;
    private Integer interval;
    private String description;

    @Override
    public TypeEnum getType() {
        return TypeEnum.ALARM;
    }

    @Getter
    @AllArgsConstructor
    public enum TriggerMode {
        CONTINUOUS("持续报警"),
        SINGLE("单次报警");

        private final String label;

    }
}
