package com.trionesdev.phecda.foundation.core.domains.device.dto;

import lombok.Data;
import com.trionesdev.phecda.foundation.core.domains.device.internal.enums.AbilityType;
import com.trionesdev.phecda.model.device.thing.ThingModelCommand;
import com.trionesdev.phecda.model.device.thing.ThingModelEvent;
import com.trionesdev.phecda.model.device.thing.ThingModelProperty;

@Data
public class ProductThingModelUpsertCmd {
    private AbilityType abilityType;
    private String identifier;
    private ThingModelProperty property;
    private ThingModelEvent event;
    private ThingModelCommand service;
}
