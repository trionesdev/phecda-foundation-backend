package ms.phecda.backend.rest.backend.domains.alarm.controller.ro;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AlarmTypeChangeEnabledRO {
    @NotNull
    private Boolean enabled;
}
