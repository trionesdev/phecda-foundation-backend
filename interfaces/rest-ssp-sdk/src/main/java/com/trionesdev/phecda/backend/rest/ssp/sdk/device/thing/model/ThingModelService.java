package com.trionesdev.phecda.backend.rest.ssp.sdk.device.thing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ThingModelService extends ThingModelAbility {
    private CallType callType;
    private Boolean required;
    private List<Param> inputParams;
    private List<Param> outputParams;

    public enum CallType {
        ASYNC,
        SYNC
    }
}
