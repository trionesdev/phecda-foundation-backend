package ms.phecda.backend.core.domains.edge.service.impl;

import com.moensun.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.edge.dao.criteria.NodeCriteria;
import ms.phecda.backend.core.domains.edge.dao.entity.Node;
import ms.phecda.backend.core.domains.edge.manager.impl.NodeManager;
import ms.phecda.backend.core.provider.ssp.device.impl.DeviceProvider;
import ms.phecda.backend.core.domains.edge.manager.impl.NodeDeviceManager;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NodeService {
    private final NodeManager nodeManager;
    private final NodeDeviceManager nodeDeviceManager;
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
}
