package ms.triones.backend.core.modules.device.support;

import ms.triones.backend.core.modules.device.dao.entity.Device;
import ms.triones.backend.core.modules.device.dao.entity.Product;
import ms.triones.backend.core.modules.device.dao.entity.ProductThingModelDraft;
import ms.triones.backend.core.modules.device.dao.entity.ProductThingModelVersion;
import ms.triones.backend.core.modules.device.manager.dto.ProductDTO;
import ms.triones.backend.core.modules.device.service.bo.DeviceEventDataBO;
import ms.triones.backend.core.modules.device.service.bo.DeviceExtBO;
import ms.triones.backend.core.modules.device.service.bo.DevicePropertyDataBO;
import ms.triones.backend.core.modules.device.service.bo.DeviceServiceDataBO;
import ms.triones.backend.core.modules.device.thing.model.ThingModelEvent;
import ms.triones.backend.core.modules.device.thing.model.ThingModelProperty;
import ms.triones.backend.core.modules.device.thing.model.ThingModelService;
import ms.triones.backend.core.provider.ssp.device.pdo.DevicePDO;
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
}
