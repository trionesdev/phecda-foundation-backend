package ms.phecda.edge.base.device.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class RemoveDeviceRequest extends BaseDeviceRequest{
    private String productId;
    private String deviceName;

}
