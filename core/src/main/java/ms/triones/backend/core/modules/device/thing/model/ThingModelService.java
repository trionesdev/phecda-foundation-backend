package ms.triones.backend.core.modules.device.thing.model;

import lombok.Data;

@Data
public class ThingModelService {
    private String identifier;
    private String name;
    private String callType;
    private Boolean required;

}
