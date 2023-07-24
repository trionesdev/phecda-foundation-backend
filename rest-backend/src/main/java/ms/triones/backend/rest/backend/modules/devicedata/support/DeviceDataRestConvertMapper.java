package ms.triones.backend.rest.backend.modules.devicedata.support;

import ms.triones.backend.core.modules.devicedata.dao.criteria.DeviceDataCriteria;
import ms.triones.backend.rest.backend.modules.devicedata.controller.query.DeviceDataQuery;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface DeviceDataRestConvertMapper {

    DeviceDataRestConvertMapper INSTANCE = Mappers.getMapper(DeviceDataRestConvertMapper.class);

    DeviceDataCriteria from(DeviceDataQuery query);
}
