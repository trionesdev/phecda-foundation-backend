package com.trionesdev.phecda.foundation.rest.tenant.domains.messageforwarding.controller.ro;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class RuleSourceDeleteRO {
    @NotBlank
    private String sourceId;
}
