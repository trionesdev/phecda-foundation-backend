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
public class ServiceInvokeMessage {
    private String messageId;
    private Long timestamp;
    private String productId;
    private String deviceName;
    private String identifier;
    private Map<String, Object> params;
}
