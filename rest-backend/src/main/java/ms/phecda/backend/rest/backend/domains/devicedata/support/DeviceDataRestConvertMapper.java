package ms.phecda.backend.rest.backend.domains.devicedata.support;

import ms.phecda.backend.core.domains.devicedata.dao.criteria.DeviceDataCriteria;
import ms.phecda.backend.rest.backend.domains.devicedata.controller.query.DeviceDataQuery;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface DeviceDataRestConvertMapper {

    DeviceDataRestConvertMapper INSTANCE = Mappers.getMapper(DeviceDataRestConvertMapper.class);

    DeviceDataCriteria from(DeviceDataQuery query);
}
