package com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.trionesdev.phecda.foundation.core.domains.device.shared.enums.AbilityType;
import com.trionesdev.phecda.model.device.thing.ThingModelCommand;
import com.trionesdev.phecda.model.device.thing.ThingModelEvent;
import com.trionesdev.phecda.model.device.thing.ThingModelProperty;


@Data
public class ProductThingModelUpsertRO {
    @NotNull
    private AbilityType abilityType;
    private String identifier;
    private ThingModelProperty property;
    private ThingModelEvent event;
    private ThingModelCommand command;
}
