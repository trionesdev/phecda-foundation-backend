package com.trionesdev.phecda.backend.rest.backend.domains.messageforwarding.controller.ro;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class MessageSourceUpdateRO {
    @NotBlank
    private String name;
    private String description;
}
