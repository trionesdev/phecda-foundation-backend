package com.trionesdev.phecda.foundation.rest.tenant.domains.notification.controller.ro;

import lombok.Data;

@Data
public class NotificationTemplateUpdateRO {
    private String code;
    private String title;
    private String content;
    private String remark;
}
