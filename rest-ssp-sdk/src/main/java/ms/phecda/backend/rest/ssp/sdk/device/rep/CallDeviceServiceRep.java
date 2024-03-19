package ms.phecda.backend.rest.ssp.sdk.device.rep;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CallDeviceServiceRep {
    private Map<String, Object> params;
}
