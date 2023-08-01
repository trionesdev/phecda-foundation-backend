package ms.triones.backend.core.modules.edge.manager.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moensun.commons.core.page.PageInfo;
import com.moensun.commons.mybatisplus.util.MpPageUtils;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.edge.dao.criteria.NodeCriteria;
import ms.triones.backend.core.modules.edge.dao.entity.Node;
import ms.triones.backend.core.modules.edge.dao.impl.NodeDAO;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class NodeManager {
    private final NodeDAO nodeDAO;

    public PageInfo<Node> page(NodeCriteria criteria) {
        Page<Node> page = nodeDAO.page(criteria);

        return MpPageUtils.of(page);
    }

    public List<Node> list() {
        return nodeDAO.list();
    }

    public void save(Node entity) {
        nodeDAO.save(entity);
    }

    public void updateById(Node entity) {
        nodeDAO.updateById(entity);
    }

    public Node getById(String id) {
        return nodeDAO.getById(id);
    }

    public void removeById(String id) {
        nodeDAO.removeById(id);
    }

    public Node getDef() {
        return nodeDAO.getDef();
    }
}
