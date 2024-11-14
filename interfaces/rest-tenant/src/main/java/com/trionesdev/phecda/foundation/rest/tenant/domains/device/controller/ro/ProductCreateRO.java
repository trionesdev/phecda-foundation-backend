package com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import com.trionesdev.phecda.foundation.core.domains.device.internal.enums.AccessChannel;
import com.trionesdev.phecda.foundation.core.domains.device.internal.enums.NodeType;
import com.trionesdev.phecda.foundation.core.domains.device.internal.enums.ProductType;


@Data
public class ProductCreateRO {
    @NotBlank
    private String name;
    @Pattern(regexp = "^(?:[a-zA-Z].*)?$", message = "ProductKey必须是英文字母开头")
    private String key;
    @NotNull
    private NodeType nodeType;
    @NotNull
    private AccessChannel accessChannel;
    private ProductType type;
    private String driverName;
}
