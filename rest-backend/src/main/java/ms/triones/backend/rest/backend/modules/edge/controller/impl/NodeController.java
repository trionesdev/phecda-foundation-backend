package ms.triones.backend.rest.backend.modules.edge.controller.impl;

import com.moensun.commons.core.page.MSPageInfo;
import com.moensun.commons.core.page.PageInfo;
import com.moensun.commons.mybatisplus.util.MpPageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.edge.dao.criteria.NodeCriteria;
import ms.triones.backend.core.modules.edge.dao.entity.Node;
import ms.triones.backend.core.modules.edge.service.impl.NodeService;
import ms.triones.backend.rest.backend.modules.edge.controller.query.NodeQuery;
import ms.triones.backend.rest.backend.modules.edge.controller.ro.NodeCreateRO;
import ms.triones.backend.rest.backend.modules.edge.controller.ro.NodeUpdateRO;
import ms.triones.backend.rest.backend.modules.edge.controller.vo.NodeVO;
import ms.triones.backend.rest.backend.modules.edge.support.NodeConvertMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Operation(summary = "列表")
    @GetMapping(value = "nodes/list")
    public List<NodeVO> list() {
        List<Node> list = nodeService.list();
        return NodeConvertMapper.INSTANT.toVoList(list);
    }

    @Operation(summary = "分页查询")
    @GetMapping(value = "nodes/page")
    public PageInfo<NodeVO> tree(@Validated NodeQuery args) {
        NodeCriteria criteria = NodeConvertMapper.INSTANT.from(args);

        PageInfo<Node> page = nodeService.page(criteria);
        List<NodeVO> rows = NodeConvertMapper.INSTANT.toVoList(page.getRows());
        return MpPageUtils.of(page, rows);
    }

    @Operation(summary = "保存")
    @PostMapping(value = "nodes")
    public void save(@Validated @RequestBody NodeCreateRO args) {
        Node entity = NodeConvertMapper.INSTANT.from(args);
        nodeService.save(entity);
    }

    @Operation(summary = "根据ID更新")
    @PutMapping(value = "nodes")
    public void update(@Validated @RequestBody NodeUpdateRO args) {
        Node entity = NodeConvertMapper.INSTANT.from(args);
        nodeService.updateById(entity);
    }

    @Operation(summary = "根据ID获取详细信息")
    @GetMapping(value = "nodes/{id}")
    public NodeVO getById(@PathVariable("id") String id) {
        Node node = nodeService.getById(id);
        return NodeConvertMapper.INSTANT.from(node);
    }

    @Operation(summary = "删除")
    @Parameter(name = "id", description = "id")
    @DeleteMapping(value = "nodes/{id}")
    public void delete(@PathVariable("id") String id) {
        nodeService.removeById(id);
    }

    @Operation(summary = "添加边缘终端设备到终端")
    @PostMapping(value = "nodes/{id}/device-children/{ids}")
    public void addChildDevice(
            @PathVariable(value = "id") String nodeId,
            @PathVariable(value = "ids") List<String> childDeviceIds) {
        nodeService.addChildDevice(nodeId, childDeviceIds);
    }

    @Operation(summary = "删除边缘终端设备（只是移除与节点之间的关系）")
    @DeleteMapping(value = "nodes/{id}/device-children/{ids}")
    public void removeChildDevice(
            @PathVariable(value = "id") String nodeId,
            @PathVariable(value = "ids") List<String> childDeviceIds) {
        nodeService.removeChildDevice(nodeId, childDeviceIds);
    }
}
