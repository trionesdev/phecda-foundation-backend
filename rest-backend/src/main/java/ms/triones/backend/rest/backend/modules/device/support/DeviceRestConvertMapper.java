package ms.triones.backend.rest.backend.modules.device.support;

import ms.triones.backend.core.modules.device.service.bo.ThingModelUpsertBO;
import ms.triones.backend.rest.backend.modules.device.controller.ro.ProductThingModelUpsertRO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface DeviceRestConvertMapper {
    DeviceRestConvertMapper INSTANT = Mappers.getMapper(DeviceRestConvertMapper.class);

    ThingModelUpsertBO from(ProductThingModelUpsertRO args);

}
