package ms.phecda.rest.ssp.sdk.device.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CallDeviceServiceReq {
    private String deviceName;
    private String identifier;
    private Map<String, Object> params;
}
