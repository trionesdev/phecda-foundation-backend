package ms.phecda.rest.ssp.modules.device.support;

import ms.phecda.backend.core.domains.device.dao.entity.Device;
import ms.phecda.rest.ssp.sdk.device.rep.DeviceRep;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true))
public interface RestDeviceConvertMapper {
    RestDeviceConvertMapper INSTANCE = Mappers.getMapper(RestDeviceConvertMapper.class);

    List<DeviceRep> from(List<Device> args);

    DeviceRep from(Device args);
}
