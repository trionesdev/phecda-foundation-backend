package ms.phecda.backend.rest.backend.domains.messageforwarding.controller.ro;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class RuleSourceCreateRO {
    @NotBlank
    private String sourceId;
}
