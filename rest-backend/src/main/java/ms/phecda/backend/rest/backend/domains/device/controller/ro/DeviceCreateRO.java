package ms.phecda.backend.rest.backend.domains.device.controller.ro;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeviceCreateRO {
    @NotBlank
    private String productId;
    @NotBlank
    private String name;
    private String remarkName;
}
