package ms.triones.backend.rest.backend.modules.device.controller.ro;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeviceUpdateRO {
    @NotBlank
    private String id;
    @NotBlank
    private String name;
    private String nodeId;
}
