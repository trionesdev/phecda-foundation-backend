package ms.triones.backend.rest.backend.modules.device.controller.ro;

import lombok.Data;
import ms.triones.backend.core.modules.device.dao.entity.enums.AbilityTypeEnum;
import ms.triones.backend.core.modules.device.thing.model.ThingModelEvent;
import ms.triones.backend.core.modules.device.thing.model.ThingModelProperty;
import ms.triones.backend.core.modules.device.thing.model.ThingModelService;

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
