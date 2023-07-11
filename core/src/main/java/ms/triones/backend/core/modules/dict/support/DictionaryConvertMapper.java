package ms.triones.backend.core.modules.dict.support;

import ms.triones.backend.core.modules.dict.dao.entity.Dictionary;
import ms.triones.backend.core.modules.dict.service.bo.DictionaryBO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true))

public interface DictionaryConvertMapper {
    DictionaryConvertMapper INSTANT = Mappers.getMapper(DictionaryConvertMapper.class);

    List<DictionaryBO> productBOFromRecord(List<Dictionary> dictionaries);


}
