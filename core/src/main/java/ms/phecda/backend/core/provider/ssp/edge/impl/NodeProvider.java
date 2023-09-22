package ms.phecda.backend.core.provider.ssp.edge.impl;

import ms.phecda.backend.core.provider.ssp.edge.pdo.NodePDO;
import ms.phecda.backend.core.modules.edge.dao.entity.Node;
import ms.phecda.backend.core.modules.edge.service.impl.NodeService;
import ms.phecda.backend.core.modules.edge.support.NodeConvertMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class NodeProvider {
    @Resource
    private NodeService nodeService;

    public NodePDO getById(String id) {
        Node node = nodeService.getById(id);
        return NodeConvertMapper.INSTANCE.toPDO(node);
    }
}
