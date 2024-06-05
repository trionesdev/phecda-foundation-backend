package ms.phecda.backend.core.domains.device.internal;

import ms.phecda.backend.core.domains.device.manager.dto.ProductDTO;
import ms.phecda.backend.core.domains.device.service.bo.DeviceEventDataBO;
import ms.phecda.backend.core.domains.device.service.bo.DeviceExtBO;
import ms.phecda.backend.core.domains.device.service.bo.DevicePropertyDataBO;
import ms.phecda.backend.core.domains.device.service.bo.DeviceServiceDataBO;
import ms.phecda.backend.core.provider.ssp.device.pdo.DevicePDO;
import ms.phecda.backend.core.domains.device.repository.po.DevicePO;
import ms.phecda.backend.core.domains.device.repository.po.ProductPO;
import ms.phecda.backend.core.domains.device.repository.po.ProductThingModelDraft;
import ms.phecda.backend.core.domains.device.repository.po.ProductThingModelVersion;
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
public interface DeviceConvert {
    DeviceConvert INSTANCE = Mappers.getMapper(DeviceConvert.class);

    @Mappings({
            @Mapping(source = "product.nodeType.label", target = "nodeTypeLabel"),
            @Mapping(source = "product.type.label", target = "typeLabel")
    })
    ProductDTO fromRecord(ProductPO product);
    
    List<ProductDTO> productDtoFromRecord(List<ProductPO> products);

    ProductThingModelVersion from(ProductThingModelDraft args);

    DeviceExtBO from(DevicePO device);

    DevicePropertyDataBO from(ThingModelProperty args);

    DeviceEventDataBO from(ThingModelEvent args);

    DeviceServiceDataBO from(ThingModelService args);

    List<DevicePDO> toPDOList(List<DevicePO> devices);

    DevicePDO toPDO(DevicePO device);
}
