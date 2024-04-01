package ms.phecda.backend.rest.backend.domains.alarm.controller.ro;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AlarmLevelUpdateRO {
    @NotNull
    private String name;
    @NotNull
    private String identifier;
    private String description;
}
