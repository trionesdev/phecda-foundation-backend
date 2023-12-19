package ms.phecda.backend.core.messageaccess.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.phecda.backend.core.messageaccess.constant.MessageType;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class BaseDeviceMessage {
    private String messageId;
    private Long timestamp;
    private MessageType messageType;
    private String productId;
    private String deviceName;
}
