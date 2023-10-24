package ms.phecda.rest.ssp.sdk.device.rep;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.phecda.rest.ssp.sdk.device.thing.model.ThingModel;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductThingModelVersionRep {
    private String id;
    private String productId;
    private ThingModel thingModel;
}
