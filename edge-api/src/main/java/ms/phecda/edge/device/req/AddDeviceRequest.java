package ms.phecda.edge.device.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class AddDeviceRequest extends BaseDeviceRequest{
    private String driver;
    private String productId;
    private String deviceName;
    private String thingModelVersion;
    private Map<String,Object> protocols;
}
