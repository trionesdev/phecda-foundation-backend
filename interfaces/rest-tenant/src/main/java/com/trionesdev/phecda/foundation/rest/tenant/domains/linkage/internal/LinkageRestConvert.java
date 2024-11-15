package com.trionesdev.phecda.foundation.rest.tenant.domains.linkage.internal;

import com.trionesdev.phecda.foundation.core.domains.linkage.dao.criteria.LinkageSceneCriteria;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.aggregate.entity.LinkageScene;
import com.trionesdev.phecda.foundation.rest.tenant.domains.linkage.controller.query.LinkageSceneQuery;
import com.trionesdev.phecda.foundation.rest.tenant.domains.linkage.controller.ro.LinkageSceneCreateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.linkage.controller.ro.LinkageSceneRuleRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.linkage.controller.ro.LinkageSceneUpdateRO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface LinkageRestConvert {
    LinkageRestConvert INSTANCE = Mappers.getMapper(LinkageRestConvert.class);

    LinkageScene from(LinkageSceneCreateRO args);

    LinkageScene from(LinkageSceneUpdateRO args);

    LinkageScene from(LinkageSceneRuleRO args);

    LinkageSceneCriteria from(LinkageSceneQuery args);
}
