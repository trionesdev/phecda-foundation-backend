package ms.phecda.edge.device.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class UpdateDeviceRequest extends BaseDeviceRequest{
    private String deviceName;

}
