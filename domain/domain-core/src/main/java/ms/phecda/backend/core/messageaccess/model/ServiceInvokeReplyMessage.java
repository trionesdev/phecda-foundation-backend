package ms.phecda.backend.core.messageaccess.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ms.phecda.backend.core.bootstrap.message.disruptor.propertiespost.PropertiesPostMessage;

@EqualsAndHashCode(callSuper = true)
@Data
public class ServiceInvokeReplyMessage extends PropertiesPostMessage {
    private String replayId;
    private String code;
    private String errMsg;
}
