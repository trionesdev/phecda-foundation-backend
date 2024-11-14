package com.trionesdev.phecda.model.device.thing;

import com.trionesdev.phecda.model.device.thing.valuetype.ValueType;
import com.trionesdev.phecda.model.device.thing.valuetype.ValueTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ValueItem {
    private String identifier;
    private String name;
    private ValueTypeEnum valueType;
    private ValueType valueSpec;
    private List<ValueType> valueSpecs;
}
