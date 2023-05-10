package ms.triones.backend.core.modules.device.thing.model;

import lombok.Data;
import ms.triones.backend.core.modules.device.thing.valuetype.ValueType;
import ms.triones.backend.core.modules.device.thing.valuetype.ValueTypeEnum;

import java.util.List;

@Data
public class ThingModelProperty {
    private String identifier;
    private String name;
    private ValueTypeEnum valueType;
    private ValueType valueSpec;
    private List<ValueType> valueSpecs;
    private Rw rw;
    private Boolean required;
    private String description;
    private Boolean custom;


    public enum Rw{
        R,
        W,
        RW
    }
}
