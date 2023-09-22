package ms.phecda.backend.core.modules.device.thing.valuetype;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ValueTypeOption {
    private String value;
    private String label;
}
