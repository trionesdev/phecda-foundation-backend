package ms.phecda.backend.rest.ssp.domains.device.support;

import ms.phecda.backend.core.domains.device.dao.entity.Device;
import ms.phecda.backend.core.messageaccess.model.ServiceInvokeReplyMessage;
import ms.phecda.backend.rest.ssp.sdk.device.rep.DeviceRep;
import ms.phecda.backend.rest.ssp.sdk.device.rep.ServiceInvokeReplyRep;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true))
public interface RestDeviceConvertMapper {
    RestDeviceConvertMapper INSTANCE = Mappers.getMapper(RestDeviceConvertMapper.class);

    List<DeviceRep> from(List<Device> args);

    DeviceRep from(Device args);

    ServiceInvokeReplyRep from(ServiceInvokeReplyMessage args);
}
