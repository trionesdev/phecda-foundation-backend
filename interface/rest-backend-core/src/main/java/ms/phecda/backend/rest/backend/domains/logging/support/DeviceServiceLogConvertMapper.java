package ms.phecda.backend.rest.backend.domains.logging.support;

import ms.phecda.backend.core.domains.logging.dao.criteria.DeviceServiceLogCriteria;
import ms.phecda.backend.rest.backend.domains.logging.controller.query.DeviceServiceLogQuery;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface DeviceServiceLogConvertMapper {
    DeviceServiceLogConvertMapper INSTANCE = Mappers.getMapper(DeviceServiceLogConvertMapper.class);

    DeviceServiceLogCriteria from(DeviceServiceLogQuery args);
}
