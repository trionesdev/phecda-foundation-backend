package ms.phecda.backend.core.domains.edge.manager.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.mybatisplus.util.MpPageUtils;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.edge.dao.entity.Node;
import ms.phecda.backend.core.domains.edge.dao.impl.NodeDAO;
import ms.phecda.backend.core.domains.edge.dao.criteria.NodeCriteria;
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
}
