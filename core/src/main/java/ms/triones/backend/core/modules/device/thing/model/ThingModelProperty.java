package ms.triones.backend.core.modules.device.thing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.triones.backend.core.modules.device.thing.valuetype.ValueType;
import ms.triones.backend.core.modules.device.thing.valuetype.ValueTypeEnum;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ThingModelProperty extends ThingModelAbility {
    private String identifier;
    private String name;
    private ValueTypeEnum valueType;
    private ValueType valueSpec;
    private List<ValueType> valueSpecs;
    private Rw rw;
    private Boolean required;

    private Boolean custom;


    public enum Rw{
        R,
        W,
        RW
    }
}
