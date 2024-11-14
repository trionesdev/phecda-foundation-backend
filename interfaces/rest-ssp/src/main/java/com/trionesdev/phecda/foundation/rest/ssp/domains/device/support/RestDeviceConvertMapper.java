package com.trionesdev.phecda.foundation.rest.ssp.domains.device.support;

import com.trionesdev.phecda.foundation.core.domains.device.dao.po.DevicePO;
import com.trionesdev.phecda.foundation.core.messageaccess.model.ServiceInvokeReplyMessage;
import com.trionesdev.phecda.foundation.rest.ssp.sdk.device.rep.DeviceRep;
import com.trionesdev.phecda.foundation.rest.ssp.sdk.device.rep.ServiceInvokeReplyRep;
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
