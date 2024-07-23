package ms.phecda.backend.rest.backend.domains.devicedata.support;

import ms.phecda.backend.core.domains.device.dao.criteria.DevicePropertyDataCriteria;
import ms.phecda.backend.rest.backend.domains.devicedata.controller.query.DeviceDataQuery;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface DeviceDataRestConvertMapper {

    DeviceDataRestConvertMapper INSTANCE = Mappers.getMapper(DeviceDataRestConvertMapper.class);

    DevicePropertyDataCriteria from(DeviceDataQuery query);
}
