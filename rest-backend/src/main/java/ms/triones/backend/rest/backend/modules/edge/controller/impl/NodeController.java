package ms.triones.backend.rest.backend.modules.edge.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.edge.dao.entity.Node;
import ms.triones.backend.core.modules.edge.service.impl.NodeService;
import ms.triones.backend.rest.backend.modules.edge.controller.vo.NodeVO;
import ms.triones.backend.rest.backend.modules.edge.support.NodeConvertMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static ms.triones.backend.rest.backend.modules.edge.support.EdgeConstants.EDGE_URI;

@Tag(name = "边缘节点")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EDGE_URI)
public class NodeController {
    private final NodeService nodeService;

    @Operation(summary = "边缘节点列表")
    @GetMapping(value = "nodes/list")
    public List<NodeVO> list() {
        List<Node> list = nodeService.list();
        return NodeConvertMapper.INSTANT.toVoList(list);
    }
}
