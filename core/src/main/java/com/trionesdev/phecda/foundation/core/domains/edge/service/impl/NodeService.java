package com.trionesdev.phecda.foundation.core.domains.edge.service.impl;

import com.trionesdev.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.edge.dao.criteria.NodeCriteria;
import com.trionesdev.phecda.foundation.core.domains.edge.dao.entity.Node;
import com.trionesdev.phecda.foundation.core.domains.edge.manager.impl.NodeManager;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NodeService {
    private final NodeManager nodeManager;

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
