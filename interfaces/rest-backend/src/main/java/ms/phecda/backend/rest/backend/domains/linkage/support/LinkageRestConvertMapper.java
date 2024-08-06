package ms.phecda.backend.rest.backend.domains.linkage.support;

import ms.phecda.backend.core.domains.linkage.dao.criteria.LinkageSceneCriteria;
import ms.phecda.backend.core.domains.linkage.dao.entity.LinkageScene;
import ms.phecda.backend.rest.backend.domains.linkage.controller.query.LinkageSceneQuery;
import ms.phecda.backend.rest.backend.domains.linkage.controller.ro.LinkageSceneCreateRO;
import ms.phecda.backend.rest.backend.domains.linkage.controller.ro.LinkageSceneRuleRO;
import ms.phecda.backend.rest.backend.domains.linkage.controller.ro.LinkageSceneUpdateRO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface LinkageRestConvertMapper {
    LinkageRestConvertMapper INSTANCE = Mappers.getMapper(LinkageRestConvertMapper.class);

    LinkageScene from(LinkageSceneCreateRO args);

    LinkageScene from(LinkageSceneUpdateRO args);

    LinkageScene from(LinkageSceneRuleRO args);

    LinkageSceneCriteria from(LinkageSceneQuery args);
}
