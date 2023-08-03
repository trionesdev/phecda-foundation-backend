package ms.triones.backend.core.modules.edge.service.impl;

import com.moensun.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.edge.dao.criteria.NodeCriteria;
import ms.triones.backend.core.modules.edge.dao.entity.Node;
import ms.triones.backend.core.modules.edge.manager.impl.NodeManager;
import ms.triones.backend.core.provider.ssp.device.impl.DeviceProvider;
import ms.triones.backend.core.provider.ssp.device.pdo.DevicePDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NodeService {
    private final NodeManager nodeManager;
    private final DeviceProvider deviceProvider;

    public PageInfo<Node> page(NodeCriteria criteria) {
        return nodeManager.page(criteria);
    }

    public void save(Node entity) {
        nodeManager.save(entity);
    }

    public void updateById(Node entity) {
        nodeManager.updateById(entity);
    }

    public Node getById(String id) {
        return nodeManager.getById(id);
    }

    public void removeById(String id) {
        nodeManager.removeById(id);
    }

    public List<Node> list() {
        return nodeManager.list();
    }

    public void addChildDevice(String nodeId, List<String> childDeviceIds) {
        List<DevicePDO> devices = deviceProvider.listById(childDeviceIds);

        List<String> canAddDevices = devices.stream()
                .filter(i -> StringUtils.isBlank(i.getNodeId()))
                .map(DevicePDO::getId)
                .collect(Collectors.toList());
        deviceProvider.updateNodeIdOfDevice(nodeId, canAddDevices);
    }

    public void removeChildDevice(String nodeId, List<String> childDeviceIds) {
        deviceProvider.removeNodeIdOfDevice(nodeId, childDeviceIds);
    }
}
