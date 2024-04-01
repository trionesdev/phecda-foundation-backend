package ms.phecda.backend.core.provider.ssp.device.pdo.thingmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ThingModelAbilityPDO {
    private String identifier;
    private String name;
    private String description;
}
