package ms.triones.backend.core.modules.device.thing.model;

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
public class ThingModelService extends ThingModelAbility {
    private CallType callType;
    private Boolean required;

    public enum CallType {
        ASYNC,
        SYNC
    }
}
