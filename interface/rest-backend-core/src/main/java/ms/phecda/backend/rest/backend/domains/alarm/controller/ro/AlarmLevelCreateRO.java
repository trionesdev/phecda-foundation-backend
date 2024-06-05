package ms.phecda.backend.rest.backend.domains.alarm.controller.ro;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class AlarmLevelCreateRO {
    @NotNull
    private String name;
    @NotNull
    private String identifier;
    private String description;
}
