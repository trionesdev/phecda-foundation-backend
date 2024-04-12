package ms.phecda.backend.core.domains.notification.service.impl;

import com.trionesdev.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.notification.dao.criteria.NotificationTemplateCriteria;
import ms.phecda.backend.core.domains.notification.dao.entity.NotificationTemplate;
import ms.phecda.backend.core.domains.notification.manager.impl.NotificationTemplateManager;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationTemplateService {
    private final NotificationTemplateManager notificationTemplateManager;

    //region template
    public void createTemplate(NotificationTemplate template) {
        notificationTemplateManager.createTemplate(template);
    }

    public void deleteTemplateById(String id) {
        notificationTemplateManager.deleteTemplateById(id);
    }

    public void updateTemplateById(NotificationTemplate template) {
        notificationTemplateManager.updateTemplateById(template);
    }

    public NotificationTemplate findTemplateById(String id) {
        return notificationTemplateManager.findTemplateById(id);
    }

    public List<NotificationTemplate> findTemplates(NotificationTemplateCriteria criteria) {
        return notificationTemplateManager.findTemplates(criteria);
    }

    public PageInfo<NotificationTemplate> findTemplatesPage(NotificationTemplateCriteria criteria) {
        return notificationTemplateManager.findTemplatesPage(criteria);
    }
    //endregion
}
