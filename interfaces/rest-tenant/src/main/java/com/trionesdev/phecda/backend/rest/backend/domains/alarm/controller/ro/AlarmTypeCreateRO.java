package com.trionesdev.phecda.backend.rest.backend.domains.alarm.controller.ro;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;



@Data
public class AlarmTypeCreateRO {
    @NotBlank
    private String name;
    @NotBlank
    private String identifier;
    private String description;
}
