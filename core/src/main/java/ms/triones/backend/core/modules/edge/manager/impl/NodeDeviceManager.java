package ms.triones.backend.core.modules.edge.manager.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moensun.commons.core.page.PageInfo;
import com.moensun.commons.mybatisplus.util.MpPageUtils;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.edge.dao.criteria.NodeCriteria;
import ms.triones.backend.core.modules.edge.dao.entity.Node;
import ms.triones.backend.core.modules.edge.dao.entity.NodeDevice;
import ms.triones.backend.core.modules.edge.dao.impl.NodeDAO;
import ms.triones.backend.core.modules.edge.dao.impl.NodeDeviceDAO;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class NodeDeviceManager {
    private final NodeDeviceDAO nodeDeviceDAO;

    public List<NodeDevice> list() {
        return nodeDeviceDAO.list();
    }

    public List<NodeDevice> listByNodeId(String nodeId) {
        return nodeDeviceDAO.listByNodeId(nodeId);
    }

    public void save(NodeDevice entity) {
        nodeDeviceDAO.save(entity);
    }

    public void saveBatch(List<NodeDevice> entityList) {
        nodeDeviceDAO.saveBatch(entityList);
    }

    public void updateById(NodeDevice entity) {
        nodeDeviceDAO.updateById(entity);
    }

    public NodeDevice getById(String id) {
        return nodeDeviceDAO.getById(id);
    }

    public void removeById(String id) {
        nodeDeviceDAO.removeById(id);
    }

    public List<NodeDevice> listByDeviceId(List<String> deviceIds) {
        return nodeDeviceDAO.listByDeviceId(deviceIds);
    }

    public void remove(String nodeId, List<String> deviceIds) {
        nodeDeviceDAO.remove(nodeId, deviceIds);
    }
}
