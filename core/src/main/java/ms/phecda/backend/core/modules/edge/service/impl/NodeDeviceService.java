package ms.phecda.backend.core.modules.edge.service.impl;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.modules.edge.dao.entity.NodeDevice;
import ms.phecda.backend.core.modules.edge.manager.impl.NodeDeviceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NodeDeviceService {
    private final NodeDeviceManager nodeDeviceManager;

    public void save(NodeDevice entity) {
        nodeDeviceManager.save(entity);
    }

    public void updateById(NodeDevice entity) {
        nodeDeviceManager.updateById(entity);
    }

    public NodeDevice getById(String id) {
        return nodeDeviceManager.getById(id);
    }

    public void removeById(String id) {
        nodeDeviceManager.removeById(id);
    }

    public List<NodeDevice> list() {
        return nodeDeviceManager.list();
    }

    public List<NodeDevice> listByNodeId(String nodeId) {
        return nodeDeviceManager.listByNodeId(nodeId);
    }

    public void add(String nodeId, List<String> deviceIds) {
        List<NodeDevice> nodeDevices = nodeDeviceManager.listByDeviceId(deviceIds);
        Set<String> boundDeviceIds = nodeDevices.stream()
                .map(NodeDevice::getDeviceId)
                .collect(Collectors.toSet());

        List<String> canAddDeviceIds = deviceIds.stream()
                .filter(i -> !boundDeviceIds.contains(i))
                .collect(Collectors.toList());

        List<NodeDevice> entityList = Lists.newArrayList();
        for (String deviceId : canAddDeviceIds) {
            entityList.add(NodeDevice.builder()
                    .nodeId(nodeId)
                    .deviceId(deviceId)
                    .build());
        }
        nodeDeviceManager.saveBatch(entityList);
    }

    public void remove(String nodeId, List<String> deviceIds) {
        nodeDeviceManager.remove(nodeId, deviceIds);
    }

    public NodeDevice getByDeviceId(String deviceId) {
        return nodeDeviceManager.getByDeviceId(deviceId);
    }
}
