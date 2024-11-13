package com.trionesdev.phecda.backend.core.domains.device.service.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.trionesdev.phecda.backend.core.domains.device.internal.enums.AbilityType;
import com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.ThingModelCommand;
import com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.ThingModelEvent;
import com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.ThingModelProperty;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ThingModelUpsertBO {
    private AbilityType abilityType;
    private String identifier;
    private ThingModelProperty property;
    private ThingModelEvent event;
    private ThingModelCommand service;
}
