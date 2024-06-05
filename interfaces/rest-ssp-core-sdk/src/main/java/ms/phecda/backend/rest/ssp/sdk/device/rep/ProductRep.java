package ms.phecda.backend.rest.ssp.sdk.device.rep;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRep {
    private String id;
    private String name;
    private String thingModelVersion;
    private String driverName;
}
