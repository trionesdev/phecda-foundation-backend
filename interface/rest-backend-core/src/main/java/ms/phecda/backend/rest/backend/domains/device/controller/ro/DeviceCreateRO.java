package ms.phecda.backend.rest.backend.domains.device.controller.ro;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class DeviceCreateRO {
    @NotBlank
    private String productKey;
    @NotBlank
    @Pattern(regexp = "^(?:[a-zA-Z].*)?$", message = "设备名称必须是英文字母开头")
    private String name;
    private String remarkName;
}
