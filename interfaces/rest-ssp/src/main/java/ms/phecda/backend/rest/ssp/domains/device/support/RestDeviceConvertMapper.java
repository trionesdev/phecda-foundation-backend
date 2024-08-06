package ms.phecda.backend.rest.ssp.domains.device.support;

import ms.phecda.backend.core.domains.device.dao.po.DevicePO;
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

    List<DeviceRep> from(List<DevicePO> args);

    DeviceRep from(DevicePO args);

    ServiceInvokeReplyRep from(ServiceInvokeReplyMessage args);
}
