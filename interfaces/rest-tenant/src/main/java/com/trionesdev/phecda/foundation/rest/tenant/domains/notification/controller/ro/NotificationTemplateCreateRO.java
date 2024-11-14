package com.trionesdev.phecda.foundation.rest.tenant.domains.notification.controller.ro;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NotificationTemplateCreateRO {
    private String code;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String remark;
}
