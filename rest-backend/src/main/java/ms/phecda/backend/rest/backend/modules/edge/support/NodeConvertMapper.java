package ms.phecda.backend.rest.backend.modules.edge.support;

import ms.phecda.backend.core.modules.edge.dao.criteria.NodeCriteria;
import ms.phecda.backend.core.modules.edge.dao.entity.Node;
import ms.phecda.backend.rest.backend.modules.edge.controller.query.NodeQuery;
import ms.phecda.backend.rest.backend.modules.edge.controller.ro.NodeCreateRO;
import ms.phecda.backend.rest.backend.modules.edge.controller.ro.NodeUpdateRO;
import ms.phecda.backend.rest.backend.modules.edge.controller.vo.NodeVO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true))
public interface NodeConvertMapper {
    NodeConvertMapper INSTANT = Mappers.getMapper(NodeConvertMapper.class);

    List<NodeVO> toVoList(List<Node> args);

    Node from(NodeCreateRO args);

    Node from(NodeUpdateRO args);

    NodeVO from(Node args);

    NodeCriteria from(NodeQuery args);
}
