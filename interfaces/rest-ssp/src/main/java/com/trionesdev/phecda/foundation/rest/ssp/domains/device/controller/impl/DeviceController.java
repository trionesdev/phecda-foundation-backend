package com.trionesdev.phecda.foundation.rest.ssp.domains.device.controller.impl;

import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.DeviceCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.DevicePO;
import com.trionesdev.phecda.foundation.core.domains.device.dto.InvokeServiceCmd;
import com.trionesdev.phecda.foundation.core.domains.device.service.impl.DeviceService;
import com.trionesdev.phecda.foundation.core.messageaccess.model.ServiceInvokeReplyMessage;
import com.trionesdev.phecda.foundation.rest.ssp.domains.device.support.RestDeviceConvertMapper;
import com.trionesdev.phecda.foundation.rest.ssp.sdk.device.DeviceRest;
import com.trionesdev.phecda.foundation.rest.ssp.sdk.device.rep.DeviceRep;
import com.trionesdev.phecda.foundation.rest.ssp.sdk.device.rep.SendServiceReqSO;
import com.trionesdev.phecda.foundation.rest.ssp.sdk.device.rep.ServiceInvokeReplyRep;
import com.trionesdev.phecda.foundation.rest.ssp.sdk.device.req.QueryDeviceReq;
import com.trionesdev.phecda.foundation.rest.ssp.internal.RestConstants;
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
        InvokeServiceCmd argsBO = InvokeServiceCmd.builder()
                .identifier(args.getIdentifier())
                .params(args.getParams())
                .body(args.getBody())
                .build();
        ServiceInvokeReplyMessage serviceInvokeReplyMessage = deviceService.invokeService(id, argsBO);
        return RestDeviceConvertMapper.INSTANCE.from(serviceInvokeReplyMessage);
    }

    @Override
    public ServiceInvokeReplyRep serviceSendByDeviceName(String name, SendServiceReqSO args) {
        InvokeServiceCmd argsBO = InvokeServiceCmd.builder()
                .identifier(args.getIdentifier())
                .params(args.getParams())
                .body(args.getBody())
                .build();
        ServiceInvokeReplyMessage serviceInvokeReplyMessage = deviceService.sendServiceWithDeviceName(name, argsBO);
        return RestDeviceConvertMapper.INSTANCE.from(serviceInvokeReplyMessage);
    }
}
