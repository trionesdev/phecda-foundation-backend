package ms.phecda.backend.core.domains.device.internal.thing.valuetype;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValueTypeString extends ValueType{
    private Integer length;
}
