package ms.phecda.backend.rest.backend.domains.device.controller.query;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
public class DevicePropertyDataQuery {
    @NotNull
    private Instant startTime;
    @NotNull
    private Instant endTime;
    @NotNull
    private String deviceName;
    @NotNull
    private String identifier;

}
