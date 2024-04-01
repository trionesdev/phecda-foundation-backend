package ms.phecda.backend.rest.backend.domains.linkage.controller.ro;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LinkageSceneUpdateRO {
    @NotBlank
    private String name;
    private String description;
}
