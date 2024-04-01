package ms.phecda.backend.rest.backend.domains.alarm.controller.ro;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AlarmTypeUpdateRO {
    @NotBlank
    private String name;
    @NotBlank
    private String identifier;
    private String description;
}
