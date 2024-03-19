package ms.phecda.backend.core.domains.device.thing.valuetype;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValueTypeFloat extends ValueType{
    private Float max;
    private Float min;
    private Float step;
    private String unit;
    private String unitName;
}
