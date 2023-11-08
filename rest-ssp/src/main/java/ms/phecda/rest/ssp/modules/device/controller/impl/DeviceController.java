package ms.phecda.rest.ssp.modules.device.controller.impl;

import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.dao.criteria.DeviceCriteria;
import ms.phecda.backend.core.domains.device.dao.entity.Device;
import ms.phecda.backend.core.domains.device.service.impl.DeviceService;
import ms.phecda.backend.core.messageaccess.model.ServiceInvokeMessageReply;
import ms.phecda.rest.ssp.modules.device.support.RestDeviceConvertMapper;
import ms.phecda.rest.ssp.sdk.device.DeviceRest;
import ms.phecda.rest.ssp.sdk.device.rep.CallDeviceServiceRep;
import ms.phecda.rest.ssp.sdk.device.rep.DeviceRep;
import ms.phecda.rest.ssp.sdk.device.req.CallDeviceServiceReq;
import ms.phecda.rest.ssp.sdk.device.req.QueryDeviceReq;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ms.phecda.rest.ssp.support.RestConstants.CONTEXT_PATH;

@RequiredArgsConstructor
@RestController(value = "phecda.ssp.DeviceController")
@RequestMapping(value = CONTEXT_PATH)
public class DeviceController implements DeviceRest {
    private final DeviceService deviceService;

    @Override
    public List<DeviceRep> find(QueryDeviceReq req) {
        DeviceCriteria criteria = DeviceCriteria.builder()
                .productId(req.getProductId())
                .ids(req.getDeviceIds())
                .build();
        List<Device> devices = deviceService.queryList(criteria);
        return RestDeviceConvertMapper.INSTANCE.from(devices);
    }

    @Override
    public DeviceRep findByName(String name) {
        Optional<Device> device = deviceService.queryByName(name);
        return RestDeviceConvertMapper.INSTANCE.from(device.orElse(null));
    }

    @Override
    public String startPushStreaming(String id) {
        return deviceService.startPushStreaming(id);
    }

    @Override
    public void stopPushStreaming(String id) {
        deviceService.stopPushStreaming(id);
    }

    @Override
    public CallDeviceServiceRep callDeviceService(CallDeviceServiceReq req) {
        ServiceInvokeMessageReply messageReply = deviceService.callDeviceService(req.getDeviceName(), req.getIdentifier(), req.getParams());
        if (Objects.isNull(messageReply)) {
            return null;
        }
        return CallDeviceServiceRep.builder().params(messageReply.getParams()).build();
    }
}
