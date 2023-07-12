package ms.triones.backend.rest.backend.modules.dict.support;

import ms.triones.backend.core.modules.dict.dao.criteria.DictionaryCriteria;
import ms.triones.backend.core.modules.dict.dao.criteria.DictionaryTypeCriteria;
import ms.triones.backend.core.modules.dict.dao.entity.Dictionary;
import ms.triones.backend.core.modules.dict.dao.entity.DictionaryType;
import ms.triones.backend.rest.backend.modules.dict.controller.query.DictionaryQuery;
import ms.triones.backend.rest.backend.modules.dict.controller.query.DictionaryTypeQuery;
import ms.triones.backend.rest.backend.modules.dict.controller.ro.DictionaryRO;
import ms.triones.backend.rest.backend.modules.dict.controller.ro.DictionaryTypeRO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))

public interface DictRestConvertMapper {
    DictRestConvertMapper INSTANCE = Mappers.getMapper(DictRestConvertMapper.class);

    Dictionary from(DictionaryRO args);

    DictionaryCriteria from(DictionaryQuery query);

    DictionaryType from(DictionaryTypeRO args);

    DictionaryTypeCriteria from(DictionaryTypeQuery args);

}
