package ms.phecda.backend.rest.backend.domains.linkage.controller.ro;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class LinkageSceneCreateRO {
    @NotBlank
    private String name;
    private String description;
}
