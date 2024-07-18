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
public class ThingModelEvent extends ThingModelAbility {
    private Type type;
    private List<ValueItem> outputData;

    @AllArgsConstructor
    @Getter
    public enum Type {
        INFO("Info"),
        WARN("Warn"),
        ERROR("Error");

        private final String value;
    }
}
