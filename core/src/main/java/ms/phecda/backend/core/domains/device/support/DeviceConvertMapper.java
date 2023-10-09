package ms.phecda.backend.core.domains.device.support;

import ms.phecda.backend.core.domains.device.manager.dto.ProductDTO;
import ms.phecda.backend.core.domains.device.service.bo.DeviceEventDataBO;
import ms.phecda.backend.core.domains.device.service.bo.DeviceExtBO;
import ms.phecda.backend.core.domains.device.service.bo.DevicePropertyDataBO;
import ms.phecda.backend.core.domains.device.service.bo.DeviceServiceDataBO;
import ms.phecda.backend.core.provider.ssp.device.pdo.DevicePDO;
import ms.phecda.backend.core.domains.device.dao.entity.Device;
import ms.phecda.backend.core.domains.device.dao.entity.Product;
import ms.phecda.backend.core.domains.device.dao.entity.ProductThingModelDraft;
import ms.phecda.backend.core.domains.device.dao.entity.ProductThingModelVersion;
import ms.phecda.backend.core.domains.device.thing.model.ThingModelEvent;
import ms.phecda.backend.core.domains.device.thing.model.ThingModelProperty;
import ms.phecda.backend.core.domains.device.thing.model.ThingModelService;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true))
public interface DeviceConvertMapper {
    DeviceConvertMapper INSTANCE = Mappers.getMapper(DeviceConvertMapper.class);

    @Mapping(source = "product.nodeType.label", target = "nodeTypeLabel")
    ProductDTO fromRecord(Product product);

    //    @Mapping(source = "nodeType.label", target = "nodeTypeLabel")
    List<ProductDTO> productDtoFromRecord(List<Product> products);

    ProductThingModelVersion from(ProductThingModelDraft args);

    DeviceExtBO from(Device device);

    DevicePropertyDataBO from(ThingModelProperty args);

    DeviceEventDataBO from(ThingModelEvent args);

    DeviceServiceDataBO from(ThingModelService args);

    List<DevicePDO> toPDOList(List<Device> devices);

    DevicePDO toPDO(Device device);
}
