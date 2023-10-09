package ms.phecda.backend.rest.backend.domains.linkage.controller.impl;

import com.moensun.commons.core.page.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.linkage.dao.criteria.LinkageSceneCriteria;
import ms.phecda.backend.core.domains.linkage.dao.entity.LinkageScene;
import ms.phecda.backend.core.domains.linkage.service.impl.LinkageSceneService;
import ms.phecda.backend.rest.backend.domains.linkage.controller.query.LinkageSceneQuery;
import ms.phecda.backend.rest.backend.domains.linkage.controller.ro.LinkageSceneCreateRO;
import ms.phecda.backend.rest.backend.domains.linkage.controller.ro.LinkageSceneRuleRO;
import ms.phecda.backend.rest.backend.domains.linkage.controller.ro.LinkageSceneUpdateRO;
import ms.phecda.backend.rest.backend.domains.linkage.support.LinkageConstants;
import ms.phecda.backend.rest.backend.domains.linkage.support.LinkageRestConvertMapper;
import ms.phecda.backend.rest.backend.domains.linkage.controller.ro.LinkageSceneEnabledRO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "联动场景")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = LinkageConstants.LINKAGE_URI)
public class LinkageSceneController {
    private final LinkageSceneService linkageSceneService;

    @Operation(summary = "场景分页查询")
    @GetMapping(value = "scenes/page")
    public PageInfo<LinkageScene> page(@Validated LinkageSceneQuery args) {
        LinkageSceneCriteria criteria = LinkageRestConvertMapper.INSTANCE.from(args);
        return linkageSceneService.page(criteria);
    }

    @Operation(summary = "新建场景")
    @PostMapping(value = "scenes")
    public void createScene(@Validated @RequestBody LinkageSceneCreateRO args) {
        LinkageScene scene = LinkageRestConvertMapper.INSTANCE.from(args);
        linkageSceneService.createScene(scene);
    }

    @Operation(summary = "修改场景")
    @PutMapping(value = "scenes/{id}")
    public void updateSceneById(
            @PathVariable(value = "id") String id,
            @Validated @RequestBody LinkageSceneUpdateRO args) {
        LinkageScene scene = LinkageRestConvertMapper.INSTANCE.from(args);
        scene.setId(id);
        linkageSceneService.updateSceneById(scene);
    }

    @Operation(summary = "根据ID修改场景联动规则")
    @PutMapping(value = "scenes/{id}/rules")
    public void updateSceneRules(
            @PathVariable(value = "id") String id,
            @Validated @RequestBody LinkageSceneRuleRO args
    ) {
        LinkageScene scene = LinkageRestConvertMapper.INSTANCE.from(args);
        scene.setId(id);
        linkageSceneService.updateSceneById(scene);
    }

    @Operation(summary = "根据ID获取联动场景")
    @GetMapping(value = "scenes/{id}")
    public LinkageScene querySceneById(
            @PathVariable(value = "id") String id
    ) {
        return linkageSceneService.querySceneById(id).orElse(null);
    }

    @Operation(summary = "联动场景启用/禁用")
    @PutMapping(value = "scenes/{id}/enabled")
    public void sceneEnabledChange(
            @PathVariable(value = "id") String id,
            @Validated @RequestBody LinkageSceneEnabledRO args
    ) {
        linkageSceneService.sceneEnabledChange(id, args.getEnabled());
    }

    @Operation(summary = "删除场景")
    @DeleteMapping(value = "scenes/{id}")
    public void removeById(@PathVariable(value = "id") String id) {
        linkageSceneService.deleteScene(id);
    }

}
