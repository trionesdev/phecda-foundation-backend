package ms.phecda.backend.rest.backend.modules.dict.support;

import ms.phecda.backend.core.modules.dict.dao.criteria.DictionaryCriteria;
import ms.phecda.backend.core.modules.dict.dao.criteria.DictionaryTypeCriteria;
import ms.phecda.backend.core.modules.dict.dao.entity.Dictionary;
import ms.phecda.backend.core.modules.dict.dao.entity.DictionaryType;
import ms.phecda.backend.rest.backend.modules.dict.controller.query.DictionaryQuery;
import ms.phecda.backend.rest.backend.modules.dict.controller.query.DictionaryTypeQuery;
import ms.phecda.backend.rest.backend.modules.dict.controller.ro.DictionaryRO;
import ms.phecda.backend.rest.backend.modules.dict.controller.ro.DictionaryTypeRO;
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
