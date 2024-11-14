package com.trionesdev.phecda.foundation.rest.tenant.domains.linkage.controller.ro;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class LinkageSceneUpdateRO {
    @NotBlank
    private String name;
    private String description;
}
