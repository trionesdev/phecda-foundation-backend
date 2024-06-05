package ms.phecda.backend.rest.backend.domains.device.controller.ro;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeviceUpdateRO {
    @NotBlank
    private String id;

    @NotBlank
    private String remarkName;
}
