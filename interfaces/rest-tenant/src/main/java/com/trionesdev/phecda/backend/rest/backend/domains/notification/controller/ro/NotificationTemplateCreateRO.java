package com.trionesdev.phecda.backend.rest.backend.domains.notification.controller.ro;

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
