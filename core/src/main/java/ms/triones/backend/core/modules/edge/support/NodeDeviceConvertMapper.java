package ms.triones.backend.core.modules.edge.support;

import ms.triones.backend.core.modules.edge.dao.entity.Node;
import ms.triones.backend.core.modules.edge.dao.entity.NodeDevice;
import ms.triones.backend.core.provider.ssp.edge.pdo.NodeDevicePDO;
import ms.triones.backend.core.provider.ssp.edge.pdo.NodePDO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface NodeDeviceConvertMapper {
    NodeDeviceConvertMapper INSTANCE = Mappers.getMapper(NodeDeviceConvertMapper.class);

    NodeDevicePDO from(NodeDevice args);
}
