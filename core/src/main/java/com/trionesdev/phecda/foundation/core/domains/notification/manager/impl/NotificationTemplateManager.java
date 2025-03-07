package com.trionesdev.phecda.foundation.core.domains.notification.manager.impl;

import com.trionesdev.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.criteria.NotificationTemplateCriteria;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.po.NotificationTemplatePO;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.impl.NotificationTemplateDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationTemplateManager {
    private final NotificationTemplateDAO notificationTemplateDAO;

    //region template
    public void createTemplate(NotificationTemplatePO template) {
        notificationTemplateDAO.save(template);
    }

    public void deleteTemplateById(String id) {
        notificationTemplateDAO.removeById(id);
    }

    public void updateTemplateById(NotificationTemplatePO template) {
        notificationTemplateDAO.updateById(template);
    }

    public NotificationTemplatePO findTemplateById(String id) {
        return notificationTemplateDAO.getById(id);
    }

    public List<NotificationTemplatePO> findTemplates(NotificationTemplateCriteria criteria) {
        return notificationTemplateDAO.selectList(criteria);
    }

    public PageInfo<NotificationTemplatePO> findTemplatesPage(NotificationTemplateCriteria criteria) {
        return notificationTemplateDAO.selectPage(criteria);
    }

    //endregion


}
