package com.trionesdev.phecda.backend.core.domains.device.internal;

import com.trionesdev.phecda.backend.core.domains.device.dto.DeviceDTO;
import com.trionesdev.phecda.backend.core.domains.device.internal.aggregate.entity.Product;
import com.trionesdev.phecda.backend.core.domains.device.dto.ProductExtDTO;
import com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.ThingModelCommand;
import com.trionesdev.phecda.backend.core.domains.device.service.bo.DeviceEventDataBO;
import com.trionesdev.phecda.backend.core.domains.device.service.bo.DeviceExtBO;
import com.trionesdev.phecda.backend.core.domains.device.service.bo.DevicePropertyDataBO;
import com.trionesdev.phecda.backend.core.domains.device.service.bo.DeviceServiceDataBO;
import com.trionesdev.phecda.backend.core.domains.device.dto.ProductDTO;
import com.trionesdev.phecda.backend.core.domains.device.dao.po.DevicePO;
import com.trionesdev.phecda.backend.core.domains.device.dao.po.ProductPO;
import com.trionesdev.phecda.backend.core.domains.device.dao.po.ProductThingModelDraft;
import com.trionesdev.phecda.backend.core.domains.device.dao.po.ProductThingModelVersion;
import com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.ThingModelEvent;
import com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.ThingModelProperty;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
@Named("deviceBeanConvert")
public interface DeviceBeanConvert {
    DeviceBeanConvert INSTANCE = Mappers.getMapper(DeviceBeanConvert.class);

    @Mappings({
            @Mapping(source = "product.nodeType.label", target = "nodeTypeLabel"),
            @Mapping(source = "product.type.label", target = "typeLabel")
    })
    ProductExtDTO fromRecord(ProductPO product);

    Product productPoToEntity(ProductPO product);
    ProductDTO productEntityToDto(Product product);
    ProductPO productEntityToPo(Product product);
    Product productDtoToEntity(ProductDTO product);

    ProductDTO poToDto(ProductPO product);

    List<ProductExtDTO> productDtoFromRecord(List<ProductPO> products);

    ProductThingModelVersion from(ProductThingModelDraft args);

    DeviceExtBO from(DevicePO device);

    DevicePropertyDataBO from(ThingModelProperty args);

    DeviceEventDataBO from(ThingModelEvent args);

    DeviceServiceDataBO from(ThingModelCommand args);

    DeviceDTO devicePoToDto(DevicePO device);
}
