package com.trionesdev.phecda.foundation.rest.tenant.domains.linkage.controller.impl;

import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.phecda.foundation.core.domains.linkage.dao.criteria.LinkageSceneCriteria;
import com.trionesdev.phecda.foundation.core.domains.linkage.dto.LinkageSceneDTO;
import com.trionesdev.phecda.foundation.core.domains.linkage.service.impl.LinkageSceneService;
import com.trionesdev.phecda.foundation.rest.tenant.domains.linkage.controller.query.LinkageSceneQuery;
import com.trionesdev.phecda.foundation.rest.tenant.domains.linkage.controller.ro.LinkageSceneCreateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.linkage.controller.ro.LinkageSceneEnabledRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.linkage.controller.ro.LinkageSceneRuleRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.linkage.controller.ro.LinkageSceneUpdateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.linkage.internal.LinkageConstants;
import com.trionesdev.phecda.foundation.rest.tenant.domains.linkage.internal.LinkageRestConvert;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "联动场景")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = LinkageConstants.LINKAGE_URI)
public class LinkageSceneController {
    private final LinkageRestConvert convert;
    private final LinkageSceneService linkageSceneService;

    @Operation(summary = "场景分页查询")
    @GetMapping(value = "scenes/page")
    public PageInfo<LinkageSceneDTO> page(
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value = "pageNum") Integer pageNum,
            @Validated LinkageSceneQuery args
    ) {
        LinkageSceneCriteria criteria = convert.from(args);
        criteria.setPageNum(pageNum);
        criteria.setPageSize(pageSize);
        return linkageSceneService.page(criteria);
    }

    @Operation(summary = "新建场景")
    @PostMapping(value = "scenes")
    public void createScene(@Validated @RequestBody LinkageSceneCreateRO args) {
        var scene = convert.from(args);
        linkageSceneService.createScene(scene);
    }

    @Operation(summary = "修改场景")
    @PutMapping(value = "scenes/{id}")
    public void updateSceneById(
            @PathVariable(value = "id") String id,
            @Validated @RequestBody LinkageSceneUpdateRO args) {
        var scene = convert.from(args);
        scene.setId(id);
        linkageSceneService.updateSceneById(scene);
    }

    @Operation(summary = "根据ID修改场景联动规则")
    @PutMapping(value = "scenes/{id}/rules")
    public void updateSceneRules(
            @PathVariable(value = "id") String id,
            @Validated @RequestBody LinkageSceneRuleRO args
    ) {
        var scene = convert.from(args);
        scene.setId(id);
        linkageSceneService.updateSceneById(scene);
    }

    @Operation(summary = "根据ID获取联动场景")
    @GetMapping(value = "scenes/{id}")
    public LinkageSceneDTO querySceneById(
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
