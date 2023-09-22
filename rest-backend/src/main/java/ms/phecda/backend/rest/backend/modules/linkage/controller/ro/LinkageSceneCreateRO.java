package ms.phecda.backend.rest.backend.modules.linkage.controller.ro;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LinkageSceneCreateRO {
    @NotBlank
    private String name;
    private String description;
}
