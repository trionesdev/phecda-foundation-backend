package com.trionesdev.phecda.foundation.core.domains.notification.service.impl;

import com.trionesdev.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.criteria.NotificationTemplateCriteria;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.po.NotificationTemplatePO;
import com.trionesdev.phecda.foundation.core.domains.notification.manager.impl.NotificationTemplateManager;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationTemplateService {
    private final NotificationTemplateManager notificationTemplateManager;

    //region template
    public void createTemplate(NotificationTemplatePO template) {
        notificationTemplateManager.createTemplate(template);
    }

    public void deleteTemplateById(String id) {
        notificationTemplateManager.deleteTemplateById(id);
    }

    public void updateTemplateById(NotificationTemplatePO template) {
        notificationTemplateManager.updateTemplateById(template);
    }

    public NotificationTemplatePO findTemplateById(String id) {
        return notificationTemplateManager.findTemplateById(id);
    }

    public List<NotificationTemplatePO> findTemplates(NotificationTemplateCriteria criteria) {
        return notificationTemplateManager.findTemplates(criteria);
    }

    public PageInfo<NotificationTemplatePO> findTemplatesPage(NotificationTemplateCriteria criteria) {
        return notificationTemplateManager.findTemplatesPage(criteria);
    }
    //endregion
}
