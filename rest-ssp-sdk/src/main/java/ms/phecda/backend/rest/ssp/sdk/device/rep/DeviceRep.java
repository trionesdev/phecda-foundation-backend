package ms.phecda.backend.rest.ssp.sdk.device.rep;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceRep {
    private String id;
    private String productId;
    private String name;
    private String remarkName;
    private String gatewayId;
    private Boolean activated;
    private Boolean enabled;
}
