package ms.phecda.backend.core.domains.device.internal;

import ms.phecda.backend.core.domains.device.internal.entity.Product;
import ms.phecda.backend.core.domains.device.manager.dto.ProductExtDTO;
import ms.phecda.backend.core.domains.device.service.bo.DeviceEventDataBO;
import ms.phecda.backend.core.domains.device.service.bo.DeviceExtBO;
import ms.phecda.backend.core.domains.device.service.bo.DevicePropertyDataBO;
import ms.phecda.backend.core.domains.device.service.bo.DeviceServiceDataBO;
import ms.phecda.backend.core.domains.device.dto.ProductDTO;
import ms.phecda.backend.core.provider.ssp.device.pdo.DevicePDO;
import ms.phecda.backend.core.domains.device.dao.po.DevicePO;
import ms.phecda.backend.core.domains.device.dao.po.ProductPO;
import ms.phecda.backend.core.domains.device.dao.po.ProductThingModelDraft;
import ms.phecda.backend.core.domains.device.dao.po.ProductThingModelVersion;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelEvent;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelProperty;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelService;
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

    ProductDTO poToDto(ProductPO product);

    List<ProductExtDTO> productDtoFromRecord(List<ProductPO> products);

    ProductThingModelVersion from(ProductThingModelDraft args);

    DeviceExtBO from(DevicePO device);

    DevicePropertyDataBO from(ThingModelProperty args);

    DeviceEventDataBO from(ThingModelEvent args);

    DeviceServiceDataBO from(ThingModelService args);

    List<DevicePDO> toPDOList(List<DevicePO> devices);

    DevicePDO toPDO(DevicePO device);
}
