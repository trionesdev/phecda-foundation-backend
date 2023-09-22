package ms.phecda.backend.rest.backend.modules.device.controller.ro;

import lombok.Data;
import ms.phecda.backend.core.modules.device.dao.entity.enums.AbilityTypeEnum;
import ms.phecda.backend.core.modules.device.thing.model.ThingModelEvent;
import ms.phecda.backend.core.modules.device.thing.model.ThingModelProperty;
import ms.phecda.backend.core.modules.device.thing.model.ThingModelService;

import javax.validation.constraints.NotNull;

@Data
public class ProductThingModelUpsertRO {
    @NotNull
    private AbilityTypeEnum abilityType;
    private String identifier;
    private ThingModelProperty property;
    private ThingModelEvent event;
    private ThingModelService service;
}
