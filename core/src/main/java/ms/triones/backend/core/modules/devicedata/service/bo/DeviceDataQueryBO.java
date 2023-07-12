package ms.triones.backend.core.modules.devicedata.service.bo;

import lombok.Data;

import java.time.Instant;

@Data
public class DeviceDataQueryBO {
    private Instant startTime;
    private Instant endTime;
    private String assetSn;
    private String deviceName;
    private String field;
}
