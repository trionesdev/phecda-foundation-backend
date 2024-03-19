package ms.phecda.backend.core.messageaccess.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeviceEventMessageReply extends BaseDeviceMessage {
    private String identifier;
    private Integer code;
    private String message;
}
