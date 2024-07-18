package ms.phecda.backend.core.domains.device.internal.model.thing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ThingModelCommand extends ThingModelAbility {
    private CallType callType;
    private Boolean required;
    private List<ValueItem> inputData;
    private List<ValueItem> outputData;

    @AllArgsConstructor
    @Getter
    public enum CallType {
        ASYNC("Async"),
        SYNC("Sync");

        private final String value;
    }
}
