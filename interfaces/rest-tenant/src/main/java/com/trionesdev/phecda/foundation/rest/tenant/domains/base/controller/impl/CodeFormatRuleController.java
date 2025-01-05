package com.trionesdev.phecda.foundation.rest.tenant.domains.base.controller.impl;

import com.trionesdev.phecda.foundation.core.domains.base.dto.CodeFormatRuleDTO;
import com.trionesdev.phecda.foundation.core.domains.base.service.impl.CodeFormatRuleService;
import com.trionesdev.phecda.foundation.rest.tenant.domains.base.controller.ro.CodeFormatRuleCreateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.base.controller.ro.CodeFormatRuleUpdateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.base.internal.BaseRestConstants;
import com.trionesdev.phecda.foundation.rest.tenant.domains.base.internal.BaseTenantRestConvert;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "编码规则")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = BaseRestConstants.BASE_PATH)
public class CodeFormatRuleController {
    private final BaseTenantRestConvert convert;
    private final CodeFormatRuleService codeFormatRuleService;

    @Operation(summary = "创建编码规则")
    @PostMapping(value = "code-format-rules")
    public void createCodeFormatRule(@Validated @RequestBody CodeFormatRuleCreateRO args) {
        var record = convert.from(args);
        codeFormatRuleService.createCodeFormatRule(record);
    }

    @Operation(summary = "根据ID删除编码规则")
    @DeleteMapping(value = "code-format-rules/{id}")
    public void deleteCodeFormatRuleById(@PathVariable(value = "id") String id) {
        codeFormatRuleService.deleteCodeFormatRuleById(id);
    }

    @Operation(summary = "根据ID修改编码规则")
    @PutMapping(value = "code-format-rules/{id}")
    public void updateCodeFormatRuleById(@PathVariable(value = "id") String id, @Validated @RequestBody CodeFormatRuleUpdateRO args) {
        var record = convert.from(args);
        record.setId(id);
        codeFormatRuleService.updateCodeFormatRuleById(record);
    }

    @Operation(summary = "根据ID获取编码规则")
    @GetMapping(value = "code-format-rules/{id}")
    public CodeFormatRuleDTO findCodeFormatRuleById(@PathVariable(value = "id") String id) {
        return codeFormatRuleService.findCodeFormatRuleById(id).orElse(null);
    }

    @Operation(summary = "获取编码规则列表")
    @GetMapping(value = "code-format-rule/list")
    public List<CodeFormatRuleDTO> findList() {
        return codeFormatRuleService.findList();
    }


}
