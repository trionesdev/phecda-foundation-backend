package com.trionesdev.phecda.foundation.rest.ssp.sdk.device.valuetype;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValueTypeStruct extends ValueType {
    private String identifier;
    private String name;
    private ValueTypeEnum childValueType;
    private ValueType valueSpec;
    private List<ValueType> valueSpecs;
}
