package ms.phecda.backend.core.domains.device.internal.thing.valuetype;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValueTypeBool extends ValueType{
    private Boolean value;
    private String name;
}
