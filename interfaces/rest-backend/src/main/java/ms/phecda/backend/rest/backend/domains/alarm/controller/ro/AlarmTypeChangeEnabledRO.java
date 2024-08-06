package ms.phecda.backend.rest.backend.domains.alarm.controller.ro;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class AlarmTypeChangeEnabledRO {
    @NotNull
    private Boolean enabled;
}
