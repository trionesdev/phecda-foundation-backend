package ms.phecda.backend.rest.ssp.domains.device.controller.impl;

import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.repository.criteria.DeviceCriteria;
import ms.phecda.backend.core.domains.device.repository.po.DevicePO;
import ms.phecda.backend.core.domains.device.service.bo.InvokeServiceArgBO;
import ms.phecda.backend.core.domains.device.service.impl.DeviceService;
import ms.phecda.backend.core.messageaccess.model.ServiceInvokeReplyMessage;
import ms.phecda.backend.rest.ssp.domains.device.support.RestDeviceConvertMapper;
import ms.phecda.backend.rest.ssp.sdk.device.DeviceRest;
import ms.phecda.backend.rest.ssp.sdk.device.rep.DeviceRep;
import ms.phecda.backend.rest.ssp.sdk.device.rep.SendServiceReqSO;
import ms.phecda.backend.rest.ssp.sdk.device.rep.ServiceInvokeReplyRep;
import ms.phecda.backend.rest.ssp.sdk.device.req.QueryDeviceReq;
import ms.phecda.backend.rest.ssp.internal.RestConstants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController(value = "phecda.ssp.DeviceController")
@RequestMapping(value = RestConstants.CONTEXT_PATH)
public class DeviceController implements DeviceRest {
    private final DeviceService deviceService;

    @Override
    public List<DeviceRep> find(QueryDeviceReq req) {
        DeviceCriteria criteria = DeviceCriteria.builder()
                .productId(req.getProductId())
                .ids(req.getDeviceIds())
                .build();
        List<DevicePO> devices = deviceService.queryList(criteria);
        return RestDeviceConvertMapper.INSTANCE.from(devices);
    }

    @Override
    public DeviceRep findByName(String name) {
        Optional<DevicePO> device = deviceService.queryByName(name);
        return RestDeviceConvertMapper.INSTANCE.from(device.orElse(null));
    }

    @Deprecated
    @Override
    public String startPushStreaming(String id) {
        return deviceService.startPushStreaming(id);
    }

    @Deprecated
    @Override
    public void stopPushStreaming(String id) {
        deviceService.stopPushStreaming(id);
    }

    @Override
    public ServiceInvokeReplyRep serviceSend(String id, SendServiceReqSO args) {
        InvokeServiceArgBO argsBO = InvokeServiceArgBO.builder()
                .identifier(args.getIdentifier())
                .params(args.getParams())
                .body(args.getBody())
                .build();
        ServiceInvokeReplyMessage serviceInvokeReplyMessage = deviceService.invokeService(id, argsBO);
        return RestDeviceConvertMapper.INSTANCE.from(serviceInvokeReplyMessage);
    }

    @Override
    public ServiceInvokeReplyRep serviceSendByDeviceName(String name, SendServiceReqSO args) {
        InvokeServiceArgBO argsBO = InvokeServiceArgBO.builder()
                .identifier(args.getIdentifier())
                .params(args.getParams())
                .body(args.getBody())
                .build();
        ServiceInvokeReplyMessage serviceInvokeReplyMessage = deviceService.sendServiceWithDeviceName(name, argsBO);
        return RestDeviceConvertMapper.INSTANCE.from(serviceInvokeReplyMessage);
    }
}
