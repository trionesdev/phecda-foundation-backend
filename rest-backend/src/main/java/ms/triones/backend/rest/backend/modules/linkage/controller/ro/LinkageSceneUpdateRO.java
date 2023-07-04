package ms.triones.backend.rest.backend.modules.linkage.controller.ro;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LinkageSceneUpdateRO {
    @NotBlank
    private String name;
    private String description;
}
