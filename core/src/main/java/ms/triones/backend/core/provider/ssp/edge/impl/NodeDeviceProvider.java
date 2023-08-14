package ms.triones.backend.core.provider.ssp.edge.impl;

import ms.triones.backend.core.modules.edge.dao.entity.Node;
import ms.triones.backend.core.modules.edge.dao.entity.NodeDevice;
import ms.triones.backend.core.modules.edge.service.impl.NodeDeviceService;
import ms.triones.backend.core.modules.edge.service.impl.NodeService;
import ms.triones.backend.core.modules.edge.support.NodeConvertMapper;
import ms.triones.backend.core.modules.edge.support.NodeDeviceConvertMapper;
import ms.triones.backend.core.provider.ssp.edge.pdo.NodeDevicePDO;
import ms.triones.backend.core.provider.ssp.edge.pdo.NodePDO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class NodeDeviceProvider {
    @Resource
    private NodeDeviceService nodeDeviceService;

    public NodeDevicePDO getByDeviceId(String deviceId) {
        NodeDevice nodeDevice = nodeDeviceService.getByDeviceId(deviceId);
        return NodeDeviceConvertMapper.INSTANCE.from(nodeDevice);
    }
}
