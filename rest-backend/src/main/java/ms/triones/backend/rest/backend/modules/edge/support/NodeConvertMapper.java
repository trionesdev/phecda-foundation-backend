package ms.triones.backend.rest.backend.modules.edge.support;

import ms.triones.backend.core.modules.edge.dao.entity.Node;
import ms.triones.backend.rest.backend.modules.edge.controller.vo.NodeVO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true))
public interface NodeConvertMapper {
    NodeConvertMapper INSTANT = Mappers.getMapper(NodeConvertMapper.class);

    List<NodeVO> toVoList(List<Node> args);
}
