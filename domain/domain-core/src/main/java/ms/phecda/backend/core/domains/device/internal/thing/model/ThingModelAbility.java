package ms.phecda.backend.core.domains.device.internal.thing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class ThingModelAbility {
    private String identifier;
    private String name;
    private String description;
}
