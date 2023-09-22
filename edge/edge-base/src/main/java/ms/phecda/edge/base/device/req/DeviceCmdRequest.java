package ms.phecda.edge.base.device.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class DeviceCmdRequest extends BaseDeviceRequest{
    private String productId;
    private String deviceName;
    private String commandName;
    private Object commandValue;
}
