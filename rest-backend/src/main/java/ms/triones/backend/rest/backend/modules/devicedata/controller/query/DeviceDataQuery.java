package ms.triones.backend.rest.backend.modules.devicedata.controller.query;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
public class DeviceDataQuery {

    @NotNull
    private Instant startTime;
    @NotNull
    private Instant endTime;
    @NotNull
    private String assetSn;
    @NotNull
    private String deviceName;
    @NotNull
    private String field;

}
