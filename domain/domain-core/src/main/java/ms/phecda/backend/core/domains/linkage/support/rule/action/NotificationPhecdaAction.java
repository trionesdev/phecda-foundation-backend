package ms.phecda.backend.core.domains.linkage.support.rule.action;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationPhecdaAction extends PhecdaAction {
    private ContactsType contactsType;
    private List<String> contactIds;
    private List<String> contactGroupIds;
    private ChannelType channelType;
    private String templateId;

    @Override
    public TypeEnum getType() {
        return TypeEnum.NOTIFICATION;
    }

    public enum ContactsType {
        CONTACTS,
        CONTACTS_GROUP
    }

    public enum ChannelType {
        SMS,
    }
}
