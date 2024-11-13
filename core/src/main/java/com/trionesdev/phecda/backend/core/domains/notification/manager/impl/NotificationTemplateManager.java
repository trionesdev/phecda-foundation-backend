package com.trionesdev.phecda.backend.core.domains.notification.manager.impl;

import com.trionesdev.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.backend.core.domains.notification.dao.criteria.NotificationTemplateCriteria;
import com.trionesdev.phecda.backend.core.domains.notification.dao.entity.NotificationTemplate;
import com.trionesdev.phecda.backend.core.domains.notification.dao.impl.NotificationTemplateDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationTemplateManager {
    private final NotificationTemplateDAO notificationTemplateDAO;

    //region template
    public void createTemplate(NotificationTemplate template) {
        notificationTemplateDAO.save(template);
    }

    public void deleteTemplateById(String id) {
        notificationTemplateDAO.removeById(id);
    }

    public void updateTemplateById(NotificationTemplate template) {
        notificationTemplateDAO.updateById(template);
    }

    public NotificationTemplate findTemplateById(String id) {
        return notificationTemplateDAO.getById(id);
    }

    public List<NotificationTemplate> findTemplates(NotificationTemplateCriteria criteria) {
        return notificationTemplateDAO.selectList(criteria);
    }

    public PageInfo<NotificationTemplate> findTemplatesPage(NotificationTemplateCriteria criteria) {
        return notificationTemplateDAO.selectPage(criteria);
    }

    //endregion


}
