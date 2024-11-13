package com.trionesdev.phecda.backend.core.domains.device.internal.model.thing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.valuetype.ValueType;
import com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.valuetype.ValueTypeEnum;

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
