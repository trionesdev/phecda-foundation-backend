package ms.triones.backend.core.modules.device.thing.valuetype;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValueTypeInt extends ValueType{
    private ValueTypeEnum valueType;
    private Integer max;
    private Integer min;
    private Integer step;
    private String unit;
    private String unitName;
}
