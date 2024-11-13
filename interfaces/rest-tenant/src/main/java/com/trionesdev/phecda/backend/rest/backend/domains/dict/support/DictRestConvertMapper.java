package com.trionesdev.phecda.backend.rest.backend.domains.dict.support;

import com.trionesdev.phecda.backend.core.domains.dict.dao.criteria.DictionaryCriteria;
import com.trionesdev.phecda.backend.core.domains.dict.dao.criteria.DictionaryTypeCriteria;
import com.trionesdev.phecda.backend.core.domains.dict.dao.entity.Dictionary;
import com.trionesdev.phecda.backend.core.domains.dict.dao.entity.DictionaryType;
import com.trionesdev.phecda.backend.rest.backend.domains.dict.controller.query.DictionaryQuery;
import com.trionesdev.phecda.backend.rest.backend.domains.dict.controller.query.DictionaryTypeQuery;
import com.trionesdev.phecda.backend.rest.backend.domains.dict.controller.ro.DictionaryRO;
import com.trionesdev.phecda.backend.rest.backend.domains.dict.controller.ro.DictionaryTypeRO;
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
