package com.trionesdev.phecda.backend.core.domains.device.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.trionesdev.phecda.backend.core.domains.device.dao.po.ProductPO.ProtocolProperty;
import com.trionesdev.phecda.backend.core.domains.device.internal.enums.AccessChannel;
import com.trionesdev.phecda.backend.core.domains.device.internal.enums.NodeType;
import com.trionesdev.phecda.backend.core.domains.device.internal.enums.ProductStatus;
import com.trionesdev.phecda.backend.core.domains.device.internal.enums.ProductType;
import com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.ThingModel;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String id;
    private String name;
    private String key;
    private String manufacturer;
    private String description;
    private NodeType nodeType;
    private AccessChannel accessChannel;
    private ProductType type;
    private List<ProtocolProperty> protocolProperties;
    private ThingModel thingModelDraft  ;
    private ThingModel thingModelCurrent  ;

    private ProductStatus status;
    private String driverName;
    private Driver driver;

    @Data
    public static class Driver {
        private String name;
        private String description;
    }
}
