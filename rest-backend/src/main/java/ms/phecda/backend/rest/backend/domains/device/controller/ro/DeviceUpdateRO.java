package ms.phecda.backend.rest.backend.domains.device.controller.ro;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeviceUpdateRO {
    @NotBlank
    private String id;
    @NotBlank
    private String remarkName;
}
