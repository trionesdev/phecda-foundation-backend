package com.trionesdev.phecda.foundation.rest.tenant.domains.notification.support;

import com.trionesdev.phecda.foundation.core.domains.notification.dao.criteria.ContactCriteria;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.criteria.ContactGroupCriteria;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.criteria.NotificationTemplateCriteria;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.po.ContactPO;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.po.ContactGroupPO;
import com.trionesdev.phecda.foundation.core.domains.notification.dao.po.NotificationTemplatePO;
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

    ContactPO from(ContactCreateRO args);

    ContactPO from(ContactUpdateRO args);

    ContactCriteria from(ContactQuery args);


    ContactGroupPO from(ContactGroupCreateRO args);

    ContactGroupPO from(ContactGroupUpdateRO args);

    ContactGroupCriteria from(ContactGroupQuery args);

    NotificationTemplatePO from(NotificationTemplateCreateRO args);
    NotificationTemplatePO from(NotificationTemplateUpdateRO args);

    NotificationTemplateCriteria from(NotificationTemplateQuery args);
}
