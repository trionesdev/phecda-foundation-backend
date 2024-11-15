package com.trionesdev.phecda.foundation.core.domains.linkage.internal;

import com.trionesdev.phecda.foundation.core.domains.linkage.dao.po.LinkageScenePO;
import com.trionesdev.phecda.foundation.core.domains.linkage.dto.LinkageSceneDTO;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.aggregate.entity.LinkageScene;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface LinkageDomainConvert {
    LinkageScenePO linkageSceneEntityToPo(LinkageScene linkageScenePO);

    LinkageScene linkageScenePoToEntity(LinkageScenePO linkageScenePO);

    LinkageSceneDTO linkageSceneEntityToDto(LinkageScene linkageScenePO);
}
