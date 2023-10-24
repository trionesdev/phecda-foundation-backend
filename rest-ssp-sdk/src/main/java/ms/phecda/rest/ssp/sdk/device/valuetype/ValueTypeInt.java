package ms.phecda.rest.ssp.sdk.device.valuetype;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("INT")
public class ValueTypeInt extends ValueType {
    private Integer max;
    private Integer min;
    private Integer step;
    private String unit;
    private String unitName;
}
