package ms.triones.backend.core.modules.device.support;

import ms.triones.backend.core.modules.device.dao.entity.Device;
import ms.triones.backend.core.modules.device.dao.entity.ProductThingModelDraft;
import ms.triones.backend.core.modules.device.dao.entity.ProductThingModelVersion;
import ms.triones.backend.core.modules.device.service.bo.DeviceExtBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeviceConvertMapper {
    DeviceConvertMapper INSTANCE = Mappers.getMapper(DeviceConvertMapper.class);

    ProductThingModelVersion from(ProductThingModelDraft args);

    DeviceExtBO from(Device device);

}
