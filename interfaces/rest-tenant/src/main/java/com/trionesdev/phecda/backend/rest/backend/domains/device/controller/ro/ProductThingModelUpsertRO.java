package com.trionesdev.phecda.backend.rest.backend.domains.device.controller.ro;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.trionesdev.phecda.backend.core.domains.device.internal.enums.AbilityType;
import com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.ThingModelCommand;
import com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.ThingModelEvent;
import com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.ThingModelProperty;


@Data
public class ProductThingModelUpsertRO {
    @NotNull
    private AbilityType abilityType;
    private String identifier;
    private ThingModelProperty property;
    private ThingModelEvent event;
    private ThingModelCommand service;
}
