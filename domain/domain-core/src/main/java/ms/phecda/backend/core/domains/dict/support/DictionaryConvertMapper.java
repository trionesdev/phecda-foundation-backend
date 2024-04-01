package ms.phecda.backend.core.domains.dict.support;

import ms.phecda.backend.core.domains.dict.dao.entity.Dictionary;
import ms.phecda.backend.core.domains.dict.service.bo.DictionaryBO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true))

public interface DictionaryConvertMapper {
    DictionaryConvertMapper INSTANT = Mappers.getMapper(DictionaryConvertMapper.class);

    List<DictionaryBO> productBOFromRecord(List<Dictionary> dictionaries);


}
