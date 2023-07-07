package ms.triones.backend.rest.backend.modules.dict.support;

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
import ms.triones.backend.core.modules.dict.dao.criteria.DictionaryTypeCriteria;
import ms.triones.backend.core.modules.dict.dao.entity.DictionaryType;
import ms.triones.backend.rest.backend.modules.dict.controller.query.DictionaryTypeQuery;
import ms.triones.backend.rest.backend.modules.dict.controller.ro.DictionaryTypeRO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true))
public interface DictionaryTypeRestConvertMapper {
    DictionaryTypeRestConvertMapper INSTANCE = Mappers.getMapper(DictionaryTypeRestConvertMapper.class);



    DictionaryType from(DictionaryTypeRO args);

    DictionaryTypeCriteria from(DictionaryTypeQuery args);
}
