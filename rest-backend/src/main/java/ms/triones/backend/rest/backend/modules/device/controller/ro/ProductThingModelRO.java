package ms.triones.backend.rest.backend.modules.device.controller.ro;

import lombok.Data;
import ms.triones.backend.core.modules.device.thing.model.ThingModelEvent;
import ms.triones.backend.core.modules.device.thing.model.ThingModelProperty;
import ms.triones.backend.core.modules.device.thing.model.ThingModelService;

import java.util.List;

@Data
public class ProductThingModelRO {
    private String abilityType;
    private List<ThingModelEvent> events;
    private List<ThingModelProperty> properties;
    private List<ThingModelService> services;
}
