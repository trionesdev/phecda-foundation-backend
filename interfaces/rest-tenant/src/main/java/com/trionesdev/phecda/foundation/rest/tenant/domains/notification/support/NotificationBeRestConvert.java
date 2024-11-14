package com.trionesdev.phecda.foundation.rest.tenant.domains.notification.support;

import com.trionesdev.phecda.foundation.core.domains.notification.dao.criteria.ContactCriteria;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.criteria.ContactGroupCriteria;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.criteria.NotificationTemplateCriteria;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.entity.Contact;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.entity.ContactGroup;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.entity.NotificationTemplate;
import com.trionesdev.phecda.foundation.rest.tenant.domains.notification.controller.query.ContactGroupQuery;
import com.trionesdev.phecda.foundation.rest.tenant.domains.notification.controller.query.ContactQuery;
import com.trionesdev.phecda.foundation.rest.tenant.domains.notification.controller.query.NotificationTemplateQuery;
import com.trionesdev.phecda.foundation.rest.tenant.domains.notification.controller.ro.ContactCreateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.notification.controller.ro.ContactGroupCreateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.notification.controller.ro.ContactGroupUpdateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.notification.controller.ro.ContactUpdateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.notification.controller.ro.NotificationTemplateCreateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.notification.controller.ro.NotificationTemplateUpdateRO;
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
