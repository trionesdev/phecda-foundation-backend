package ms.phecda.edge.commons.valuetype;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValueTypeBool extends ValueType{
    private Boolean value;
    private String name;
}
