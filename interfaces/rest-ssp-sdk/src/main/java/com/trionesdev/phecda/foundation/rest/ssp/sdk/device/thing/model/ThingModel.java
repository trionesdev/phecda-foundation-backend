package com.trionesdev.phecda.foundation.rest.ssp.sdk.device.thing.model;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThingModel {
    private List<ThingModelEvent> events = Lists.newArrayList();
    private List<ThingModelProperty> properties = Lists.newArrayList();
    private List<ThingModelService> services = Lists.newArrayList();
}
