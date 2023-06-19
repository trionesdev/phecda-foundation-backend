package ms.phecda.edge.device.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class AddDeviceRequest extends BaseDeviceRequest{
    private String device;
    private String deviceName;

}
