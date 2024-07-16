package ms.phecda.backend.core.domains.device.dto;

import com.google.common.collect.Lists;
import lombok.Data;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelEvent;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelProperty;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelCommand;

import java.util.List;

@Data
public class ThingModelDTO {
    private List<ThingModelEvent> events = Lists.newArrayList();
    private List<ThingModelProperty> properties = Lists.newArrayList();
    private List<ThingModelCommand> services = Lists.newArrayList();
}
