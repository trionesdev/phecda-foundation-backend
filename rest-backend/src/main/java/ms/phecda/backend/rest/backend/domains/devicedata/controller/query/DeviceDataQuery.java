package ms.phecda.backend.rest.backend.domains.devicedata.controller.query;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
public class DeviceDataQuery {
    @NotNull
    private Instant startTime;
    @NotNull
    private Instant endTime;
    private String assetSn;
    @NotNull
    private String deviceName;
    @NotNull
    private String field;

}
