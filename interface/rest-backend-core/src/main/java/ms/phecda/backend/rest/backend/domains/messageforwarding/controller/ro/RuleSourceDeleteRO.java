package ms.phecda.backend.rest.backend.domains.messageforwarding.controller.ro;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RuleSourceDeleteRO {
    @NotBlank
    private String sourceId;
}
