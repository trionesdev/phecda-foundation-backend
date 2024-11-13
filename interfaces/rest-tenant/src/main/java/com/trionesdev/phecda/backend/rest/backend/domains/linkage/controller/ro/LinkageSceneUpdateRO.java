package com.trionesdev.phecda.backend.rest.backend.domains.linkage.controller.ro;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class LinkageSceneUpdateRO {
    @NotBlank
    private String name;
    private String description;
}
