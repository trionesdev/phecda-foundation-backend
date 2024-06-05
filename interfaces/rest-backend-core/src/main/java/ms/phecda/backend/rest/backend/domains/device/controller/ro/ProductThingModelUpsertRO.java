package ms.phecda.backend.rest.backend.domains.device.controller.ro;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ms.phecda.backend.core.domains.device.repository.po.enums.AbilityTypeEnum;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelEvent;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelProperty;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelService;


@Data
public class ProductThingModelUpsertRO {
    @NotNull
    private AbilityTypeEnum abilityType;
    private String identifier;
    private ThingModelProperty property;
    private ThingModelEvent event;
    private ThingModelService service;
}
