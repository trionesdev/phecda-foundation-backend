package ms.phecda.backend.core.domains.device.internal.model.thing.valuetype;

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
@JsonTypeName("LONG")
public class ValueTypeLong extends ValueType{
    private Integer max;
    private Integer min;
    private Integer step;
    private String unit;
    private String unitName;
}
