package ms.phecda.backend.core.domains.device.thing.valuetype;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValueTypeArray extends ValueType{
    private ValueTypeEnum childValueType;
    private List<ValueType> valueSpecs;
    private Integer size;
}
