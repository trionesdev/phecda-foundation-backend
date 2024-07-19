package ms.phecda.backend.rest.backend.domains.device.controller.ro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import ms.phecda.backend.core.domains.device.dao.po.ProductPO;
import ms.phecda.backend.core.domains.device.dao.po.ProductPO.ProtocolProperty;
import ms.phecda.backend.core.domains.device.internal.enums.AccessChannel;
import ms.phecda.backend.core.domains.device.internal.enums.NodeType;
import ms.phecda.backend.core.domains.device.internal.enums.ProductType;

import java.util.ArrayList;
import java.util.List;

public class ProductRO {

    @Data
    public static class Create {
        @NotBlank
        private String name;
        @Pattern(regexp = "^(?:[a-zA-Z].*)?$", message = "ProductKey必须是英文字母开头")
        private String key;
        private String manufacturer;
        private String description;
        @NotNull
        private NodeType nodeType;
        private AccessChannel accessChannel;
        private ProductType type;
        private String driverName;
    }

    @Data
    public static class Update {
        @NotBlank
        private String name;
        @Pattern(regexp = "^(?:[a-zA-Z].*)?$", message = "ProductKey必须是英文字母开头")
        private String key;
        private String manufacturer;
        private String description;
        @NotNull
        private NodeType nodeType;
        private AccessChannel accessChannel;
        private ProductType type;
        private String driverName;
    }

    @Data
    public static class ProtocolPropertiesUpdate{
        private List<ProtocolProperty> protocolProperties = new ArrayList<>();
    }

}
