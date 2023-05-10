package ms.triones.backend.core.modules.device.thing.valuetype;

import lombok.Data;

@Data
public class ValueTypeString extends ValueType{
    private ValueTypeEnum valueType;
    private Integer length;
}
