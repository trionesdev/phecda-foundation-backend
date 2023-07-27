package ms.triones.backend.rest.backend.modules.logging.support;

import ms.triones.backend.core.modules.logging.dao.criteria.DeviceEventLogCriteria;
import ms.triones.backend.rest.backend.modules.logging.controller.query.DeviceEventLogQuery;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface DeviceEventLogConvertMapper {
    DeviceEventLogConvertMapper INSTANCE = Mappers.getMapper(DeviceEventLogConvertMapper.class);

    DeviceEventLogCriteria from(DeviceEventLogQuery args);
}
