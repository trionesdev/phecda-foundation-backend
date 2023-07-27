package ms.triones.backend.rest.backend.modules.logging.support;

import ms.triones.backend.core.modules.logging.dao.criteria.DeviceServiceLogCriteria;
import ms.triones.backend.rest.backend.modules.logging.controller.query.DeviceServiceLogQuery;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface DeviceServiceLogConvertMapper {
    DeviceServiceLogConvertMapper INSTANCE = Mappers.getMapper(DeviceServiceLogConvertMapper.class);

    DeviceServiceLogCriteria from(DeviceServiceLogQuery args);
}
