package com.trionesdev.phecda.model.device.thing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trionesdev.phecda.model.device.thing.enums.Rw;
import com.trionesdev.phecda.model.device.thing.valuetype.ValueType;
import com.trionesdev.phecda.model.device.thing.valuetype.ValueTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 物模型属性
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThingModelProperty extends ThingModelAbility {
    private ValueTypeEnum valueType;
    private ValueType valueSpec;
    private List<ValueType> valueSpecs;
    private Rw rw;
    private Boolean required;
    private Boolean custom;

}
