package ms.phecda.backend.core.messageaccess.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServiceInvokeMessageReply extends BaseDeviceMessage {
    private String identifier;
    private Map<String, Object> params;
    private Integer code;
    private String message;
}
