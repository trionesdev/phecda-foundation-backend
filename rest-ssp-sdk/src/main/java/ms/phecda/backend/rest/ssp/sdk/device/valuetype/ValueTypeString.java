package ms.phecda.backend.rest.ssp.sdk.device.valuetype;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValueTypeString extends ValueType {
    private Integer length;
}
