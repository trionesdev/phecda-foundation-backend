package ms.phecda.backend.rest.backend.modules.devicedata.support;

import ms.phecda.backend.core.modules.devicedata.dao.criteria.DeviceDataCriteria;
import ms.phecda.backend.rest.backend.modules.devicedata.controller.query.DeviceDataQuery;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface DeviceDataRestConvertMapper {

    DeviceDataRestConvertMapper INSTANCE = Mappers.getMapper(DeviceDataRestConvertMapper.class);

    DeviceDataCriteria from(DeviceDataQuery query);
}
