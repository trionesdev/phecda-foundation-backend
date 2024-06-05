package ms.phecda.backend.rest.backend.domains.device.controller.ro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class DeviceCreateRO {
    @NotBlank
    private String productKey;
    @NotBlank
    @Pattern(regexp = "^(?:[a-zA-Z].*)?$", message = "设备名称必须是英文字母开头")
    private String name;
    private String remarkName;
}
