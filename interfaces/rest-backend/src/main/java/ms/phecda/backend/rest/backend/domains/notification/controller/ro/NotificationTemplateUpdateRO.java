package ms.phecda.backend.rest.backend.domains.notification.controller.ro;

import lombok.Data;

@Data
public class NotificationTemplateUpdateRO {
    private String code;
    private String title;
    private String content;
    private String remark;
}
