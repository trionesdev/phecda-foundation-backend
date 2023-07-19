package ms.triones.backend.core.modules.device.thing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.phecda.edge.commons.valuetype.ValueType;
import ms.phecda.edge.commons.valuetype.ValueTypeEnum;

import java.util.List;

@EqualsAndHashCode
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Param {
    private String identifier;
    private String name;
    private ValueTypeEnum valueType;
    private ValueType valueSpec;
    private List<ValueType> valueSpecs;
}
