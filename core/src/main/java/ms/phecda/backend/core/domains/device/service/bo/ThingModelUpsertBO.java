package ms.phecda.backend.core.domains.device.service.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.phecda.backend.core.domains.device.dao.entity.enums.AbilityTypeEnum;
import ms.phecda.backend.core.domains.device.thing.model.ThingModelEvent;
import ms.phecda.backend.core.domains.device.thing.model.ThingModelProperty;
import ms.phecda.backend.core.domains.device.thing.model.ThingModelService;

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
