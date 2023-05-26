package ms.triones.backend.core.modules.device.thing.valuetype;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValueTypeBool extends ValueType{
    private ValueTypeEnum valueType;
    private Boolean value;
    private String name;
}
