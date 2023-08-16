package ms.triones.backend.core.messageaccess.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WritePropertyMessageReply {
    private String messageId;
    private Long timestamp;
    private String deviceName;
}
