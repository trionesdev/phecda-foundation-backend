package com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.valuetype;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValueTypeDouble extends ValueType{
    private Double max;
    private Double min;
    private Double step;
    private String unit;
    private String unitName;
}
