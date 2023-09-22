package ms.phecda.backend.core.modules.edge.support;

import ms.phecda.backend.core.modules.edge.dao.entity.NodeDevice;
import ms.phecda.backend.core.provider.ssp.edge.pdo.NodeDevicePDO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true))
public interface NodeDeviceConvertMapper {
    NodeDeviceConvertMapper INSTANCE = Mappers.getMapper(NodeDeviceConvertMapper.class);

    NodeDevicePDO from(NodeDevice args);

    List<NodeDevicePDO> toPDOList(List<NodeDevice> args);
}
