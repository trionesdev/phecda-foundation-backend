package ms.phecda.backend.rest.backend.domains.device.controller.ro;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductDriverCreateRO {
    @NotBlank
    private String name;
}
