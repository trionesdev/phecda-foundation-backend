package ms.triones.backend.core.modules.device.service.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.triones.backend.core.modules.device.dao.entity.enums.AbilityTypeEnum;
import ms.triones.backend.core.modules.device.thing.model.ThingModelEvent;
import ms.triones.backend.core.modules.device.thing.model.ThingModelProperty;
import ms.triones.backend.core.modules.device.thing.model.ThingModelService;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ThingModelUpsertBO {
    private AbilityTypeEnum abilityType;
    private String identifier;
    private ThingModelProperty property;
    private ThingModelEvent event;
    private ThingModelService service;
}
