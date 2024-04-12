package ms.phecda.backend.rest.backend.domains.notification.support;

import ms.phecda.backend.core.domains.notification.dao.criteria.ContactCriteria;
import ms.phecda.backend.core.domains.notification.dao.criteria.ContactGroupCriteria;
import ms.phecda.backend.core.domains.notification.dao.criteria.NotificationTemplateCriteria;
import ms.phecda.backend.core.domains.notification.dao.entity.Contact;
import ms.phecda.backend.core.domains.notification.dao.entity.ContactGroup;
import ms.phecda.backend.core.domains.notification.dao.entity.NotificationTemplate;
import ms.phecda.backend.rest.backend.domains.notification.controller.query.ContactGroupQuery;
import ms.phecda.backend.rest.backend.domains.notification.controller.query.ContactQuery;
import ms.phecda.backend.rest.backend.domains.notification.controller.query.NotificationTemplateQuery;
import ms.phecda.backend.rest.backend.domains.notification.controller.ro.ContactCreateRO;
import ms.phecda.backend.rest.backend.domains.notification.controller.ro.ContactGroupCreateRO;
import ms.phecda.backend.rest.backend.domains.notification.controller.ro.ContactGroupUpdateRO;
import ms.phecda.backend.rest.backend.domains.notification.controller.ro.ContactUpdateRO;
import ms.phecda.backend.rest.backend.domains.notification.controller.ro.NotificationTemplateCreateRO;
import ms.phecda.backend.rest.backend.domains.notification.controller.ro.NotificationTemplateUpdateRO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
@Named("notificationBeRestConvert")
public interface NotificationBeRestConvert {

    Contact from(ContactCreateRO args);

    Contact from(ContactUpdateRO args);

    ContactCriteria from(ContactQuery args);


    ContactGroup from(ContactGroupCreateRO args);

    ContactGroup from(ContactGroupUpdateRO args);

    ContactGroupCriteria from(ContactGroupQuery args);

    NotificationTemplate from(NotificationTemplateCreateRO args);
    NotificationTemplate from(NotificationTemplateUpdateRO args);

    NotificationTemplateCriteria from(NotificationTemplateQuery args);
}
