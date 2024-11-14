package com.trionesdev.phecda.foundation.rest.tenant.domains.messageforwarding.controller.ro;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class MessageSourceUpdateRO {
    @NotBlank
    private String name;
    private String description;
}
