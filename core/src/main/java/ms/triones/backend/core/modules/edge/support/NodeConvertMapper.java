package ms.triones.backend.core.modules.edge.support;

import ms.triones.backend.core.modules.device.dao.entity.Device;
import ms.triones.backend.core.modules.device.dao.entity.Product;
import ms.triones.backend.core.modules.device.dao.entity.ProductThingModelDraft;
import ms.triones.backend.core.modules.device.dao.entity.ProductThingModelVersion;
import ms.triones.backend.core.modules.device.manager.dto.ProductDTO;
import ms.triones.backend.core.modules.device.service.bo.DeviceEventDataBO;
import ms.triones.backend.core.modules.device.service.bo.DeviceExtBO;
import ms.triones.backend.core.modules.device.service.bo.DevicePropertyDataBO;
import ms.triones.backend.core.modules.device.service.bo.DeviceServiceDataBO;
import ms.triones.backend.core.modules.device.thing.model.ThingModelEvent;
import ms.triones.backend.core.modules.device.thing.model.ThingModelProperty;
import ms.triones.backend.core.modules.device.thing.model.ThingModelService;
import ms.triones.backend.core.modules.edge.dao.entity.Node;
import ms.triones.backend.core.provider.ssp.edge.pdo.NodePDO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true))
public interface NodeConvertMapper {
    NodeConvertMapper INSTANCE = Mappers.getMapper(NodeConvertMapper.class);

    NodePDO toPDO(Node args);
}
