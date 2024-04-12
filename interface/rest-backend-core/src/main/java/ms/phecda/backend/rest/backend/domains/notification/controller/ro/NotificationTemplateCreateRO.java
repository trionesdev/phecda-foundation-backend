package ms.phecda.backend.rest.backend.domains.notification.controller.ro;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NotificationTemplateCreateRO {
    private String code;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String remark;
}
