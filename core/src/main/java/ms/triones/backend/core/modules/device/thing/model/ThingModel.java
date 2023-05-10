package ms.triones.backend.core.modules.device.thing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThingModel {
    private List<ThingModelEvent> events;
    private List<ThingModelProperty> properties;
    private List<ThingModelService> services;
}
