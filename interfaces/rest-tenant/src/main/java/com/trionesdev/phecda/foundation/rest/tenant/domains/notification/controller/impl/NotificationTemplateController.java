package com.trionesdev.phecda.foundation.rest.tenant.domains.notification.controller.impl;

import com.trionesdev.commons.core.page.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.criteria.NotificationTemplateCriteria;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.po.NotificationTemplatePO;
import com.trionesdev.phecda.foundation.core.domains.notification.service.impl.NotificationTemplateService;
import com.trionesdev.phecda.foundation.rest.tenant.domains.notification.controller.query.NotificationTemplateQuery;
import com.trionesdev.phecda.foundation.rest.tenant.domains.notification.controller.ro.NotificationTemplateCreateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.notification.controller.ro.NotificationTemplateUpdateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.notification.support.NotificationBeRestConvert;
import com.trionesdev.phecda.foundation.rest.tenant.domains.notification.support.NotificationConstants;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "通知模板")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = NotificationConstants.NOTIFICATION_URI)
public class NotificationTemplateController {
    private final NotificationBeRestConvert notificationBeRestConvert;
    private final NotificationTemplateService notificationTemplateService;

    @Operation(summary = "新建通知模板")
    @PostMapping(value = "templates")
    public void createTemplate(@RequestBody NotificationTemplateCreateRO args) {
        NotificationTemplatePO template = notificationBeRestConvert.from(args);
        notificationTemplateService.createTemplate(template);
    }

    @Operation(summary = "根据ID删除通知模板")
    @DeleteMapping(value = "templates/{id}")
    public void deleteTemplateById(@PathVariable String id) {
        notificationTemplateService.deleteTemplateById(id);
    }

    @Operation(summary = "根据ID修改通知模板")
    @PutMapping(value = "templates/{id}")
    public void updateTemplateById(@PathVariable String id, @RequestBody NotificationTemplateUpdateRO args) {
        NotificationTemplatePO template = notificationBeRestConvert.from(args);
        template.setId(id);
        notificationTemplateService.updateTemplateById(template);
    }

    @Operation(summary = "根据ID获取通知模板")
    @GetMapping(value = "templates/{id}")
    public NotificationTemplatePO findTemplateById(@PathVariable String id) {
        return notificationTemplateService.findTemplateById(id);
    }

    @Operation(summary = "查询通知模板列表")
    @GetMapping(value = "templates/list")
    public List<NotificationTemplatePO> findTemplates(NotificationTemplateQuery query) {
        return notificationTemplateService.findTemplates(notificationBeRestConvert.from(query));
    }

    @Operation(summary = "分页查询通知模板")
    @GetMapping(value = "templates/page")
    public PageInfo<NotificationTemplatePO> findTemplatesPage(
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value = "pageNum") Integer pageNum,
            NotificationTemplateQuery query
    ) {
        NotificationTemplateCriteria criteria = notificationBeRestConvert.from(query);
        criteria.setPageSize(pageSize);
        criteria.setPageNum(pageNum);
        return notificationTemplateService.findTemplatesPage(criteria);
    }

}
