package ms.phecda.backend.rest.backend.domains.device.controller.ro;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DriverRO {

    @Data
    public static class Create{
        @NotBlank
        private String name;
        private String description;
    }

    @Data
    public static class Update{
        @NotBlank
        private String name;
        private String description;
    }

}
