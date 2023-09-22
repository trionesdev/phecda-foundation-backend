package ms.phecda.edge.base.commons.valuetype;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValueTypeString extends ValueType{
    private Integer length;
}
