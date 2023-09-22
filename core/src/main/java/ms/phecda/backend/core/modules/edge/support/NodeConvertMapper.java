package ms.phecda.backend.core.modules.edge.support;

import ms.phecda.backend.core.modules.edge.dao.entity.Node;
import ms.phecda.backend.core.provider.ssp.edge.pdo.NodePDO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface NodeConvertMapper {
    NodeConvertMapper INSTANCE = Mappers.getMapper(NodeConvertMapper.class);

    NodePDO toPDO(Node args);
}
