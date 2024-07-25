package ms.phecda.backend.core.domains.device.internal.model.thing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThingModel {
    private List<ThingModelEvent> events = Lists.newArrayList();
    private List<ThingModelProperty> properties = Lists.newArrayList();
    private List<ThingModelCommand> commands = Lists.newArrayList();
}
