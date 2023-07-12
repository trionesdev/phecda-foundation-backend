package ms.triones.backend.rest.backend.modules.devicedata.controller.query;

import lombok.Data;

import java.time.Instant;

@Data
public class DeviceDataQuery {

    private Instant startTime;
    private Instant endTime;
    private String assetSn;
    private String deviceName;
    private String field;

}
