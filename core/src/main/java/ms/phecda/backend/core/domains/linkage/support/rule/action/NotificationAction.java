package ms.phecda.backend.core.domains.linkage.support.rule.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationAction extends Action {
    private ContactsType contactsType;
    private String channel;
    private String template;

    public enum ContactsType {
        CONTACTS,
        CONTACTS_GROUP
    }
}
