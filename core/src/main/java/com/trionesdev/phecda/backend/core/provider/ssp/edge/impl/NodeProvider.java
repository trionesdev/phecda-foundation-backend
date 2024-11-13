package com.trionesdev.phecda.backend.core.provider.ssp.edge.impl;

import jakarta.annotation.Resource;
import com.trionesdev.phecda.backend.core.provider.ssp.edge.pdo.NodePDO;
import com.trionesdev.phecda.backend.core.domains.edge.dao.entity.Node;
import com.trionesdev.phecda.backend.core.domains.edge.service.impl.NodeService;
import com.trionesdev.phecda.backend.core.domains.edge.support.NodeConvertMapper;
import org.springframework.stereotype.Component;

@Component
public class NodeProvider {
    @Resource
    private NodeService nodeService;

    public NodePDO getById(String id) {
        Node node = nodeService.getById(id);
        return NodeConvertMapper.INSTANCE.toPDO(node);
    }
}
