package com.trionesdev.phecda.backend.rest.ssp.sdk.device.thing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.trionesdev.phecda.backend.rest.ssp.sdk.device.valuetype.ValueType;
import com.trionesdev.phecda.backend.rest.ssp.sdk.device.valuetype.ValueTypeEnum;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ThingModelProperty extends ThingModelAbility {
    private ValueTypeEnum valueType;
    private ValueType valueSpec;
    private List<ValueType> valueSpecs;
    private Rw rw;
    private Boolean required;
    private Boolean custom;

    public enum Rw {
        R,
        W,
        RW
    }
}
