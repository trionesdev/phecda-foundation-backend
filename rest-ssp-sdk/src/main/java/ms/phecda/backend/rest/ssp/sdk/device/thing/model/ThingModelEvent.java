package ms.phecda.backend.rest.ssp.sdk.device.thing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ThingModelEvent extends ThingModelAbility {
    private Type type;
    private List<Param> outputParams;

    public enum Type {
        INFO,
        WARN,
        ERROR,
    }
}
