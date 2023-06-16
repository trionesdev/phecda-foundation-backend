package ms.triones.backend.core.modules.device.support;

import ms.triones.backend.core.modules.device.dao.entity.Device;
import ms.triones.backend.core.modules.device.dao.entity.Product;
import ms.triones.backend.core.modules.device.dao.entity.ProductThingModelDraft;
import ms.triones.backend.core.modules.device.dao.entity.ProductThingModelVersion;
import ms.triones.backend.core.modules.device.manager.dto.ProductDTO;
import ms.triones.backend.core.modules.device.service.bo.DeviceExtBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DeviceConvertMapper {
    DeviceConvertMapper INSTANCE = Mappers.getMapper(DeviceConvertMapper.class);

    @Mapping(source = "product.nodeType.label", target = "nodeTypeLabel")
    ProductDTO fromRecord(Product product);

//    @Mapping(source = "nodeType.label", target = "nodeTypeLabel")
    List<ProductDTO> productDtoFromRecord(List<Product> products);

    ProductThingModelVersion from(ProductThingModelDraft args);

    DeviceExtBO from(Device device);

}
