package com.trionesdev.phecda.foundation.core.facade.ssp.edge.impl;

import jakarta.annotation.Resource;
import com.trionesdev.phecda.foundation.core.facade.ssp.edge.pdo.NodeDevicePDO;
import com.trionesdev.phecda.foundation.core.domains.edge.dao.entity.NodeDevice;
import com.trionesdev.phecda.foundation.core.domains.edge.service.impl.NodeDeviceService;
import com.trionesdev.phecda.foundation.core.domains.edge.support.NodeDeviceConvertMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NodeDeviceProvider {
    @Resource
    private NodeDeviceService nodeDeviceService;

    public NodeDevicePDO getByDeviceId(String deviceId) {
        NodeDevice nodeDevice = nodeDeviceService.getByDeviceId(deviceId);
        return NodeDeviceConvertMapper.INSTANCE.from(nodeDevice);
    }

    public List<NodeDevicePDO> listByNodeId(String nodeId) {
        List<NodeDevice> nodeDevices = nodeDeviceService.listByNodeId(nodeId);
        return NodeDeviceConvertMapper.INSTANCE.toPDOList(nodeDevices);
    }
}
