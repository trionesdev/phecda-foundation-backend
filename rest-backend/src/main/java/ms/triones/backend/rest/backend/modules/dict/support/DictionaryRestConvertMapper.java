package ms.triones.backend.rest.backend.modules.dict.support;

import ms.triones.backend.core.modules.dict.dao.criteria.DictionaryCriteria;
import ms.triones.backend.core.modules.dict.dao.entity.Dictionary;
import ms.triones.backend.rest.backend.modules.dict.controller.query.DictionaryQuery;
import ms.triones.backend.rest.backend.modules.dict.controller.ro.DictionaryRO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(builder = @Builder(disableBuilder = true))

public interface DictionaryRestConvertMapper {
    DictionaryRestConvertMapper INSTANT = Mappers.getMapper(DictionaryRestConvertMapper.class);

    Dictionary form(DictionaryRO args);
    DictionaryCriteria form(DictionaryQuery query);

}
