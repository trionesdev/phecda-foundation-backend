package ms.phecda.backend.core.domains.device.dto;

import lombok.Data;
import ms.phecda.backend.core.domains.device.internal.enums.AbilityType;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelCommand;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelEvent;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelProperty;

@Data
public class ProductThingModelUpsertCmd {
    private AbilityType abilityType;
    private String identifier;
    private ThingModelProperty property;
    private ThingModelEvent event;
    private ThingModelCommand service;
}
