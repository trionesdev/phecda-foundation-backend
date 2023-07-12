package ms.triones.backend.core.modules.devicedata.service.bo;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class DeviceDataBO {

    private Instant time;
    private Object value;


    private String field;
    private String assetSn;
    private String deviceName;
}
