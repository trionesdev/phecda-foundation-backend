package com.trionesdev.phecda.backend.rest.backend.domains.linkage.support;

import com.trionesdev.phecda.backend.core.domains.linkage.dao.criteria.LinkageSceneCriteria;
import com.trionesdev.phecda.backend.core.domains.linkage.dao.po.LinkageScenePO;
import com.trionesdev.phecda.backend.rest.backend.domains.linkage.controller.query.LinkageSceneQuery;
import com.trionesdev.phecda.backend.rest.backend.domains.linkage.controller.ro.LinkageSceneCreateRO;
import com.trionesdev.phecda.backend.rest.backend.domains.linkage.controller.ro.LinkageSceneRuleRO;
import com.trionesdev.phecda.backend.rest.backend.domains.linkage.controller.ro.LinkageSceneUpdateRO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface LinkageRestConvertMapper {
    LinkageRestConvertMapper INSTANCE = Mappers.getMapper(LinkageRestConvertMapper.class);

    LinkageScenePO from(LinkageSceneCreateRO args);

    LinkageScenePO from(LinkageSceneUpdateRO args);

    LinkageScenePO from(LinkageSceneRuleRO args);

    LinkageSceneCriteria from(LinkageSceneQuery args);
}
