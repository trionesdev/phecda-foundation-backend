package ms.phecda.backend.core.provider.ssp.device.pdo.thingmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.phecda.backend.core.domains.device.internal.thing.model.ThingModelProperty;
import ms.phecda.backend.core.domains.device.internal.thing.valuetype.ValueType;
import ms.phecda.backend.core.domains.device.internal.thing.valuetype.ValueTypeEnum;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThingModelPropertyPDO extends ThingModelAbilityPDO{
    private ValueTypeEnum valueType;
    private ValueType valueSpec;
    private List<ValueType> valueSpecs;
    private ThingModelProperty.Rw rw;
    private Boolean required;
    private Boolean custom;
}
