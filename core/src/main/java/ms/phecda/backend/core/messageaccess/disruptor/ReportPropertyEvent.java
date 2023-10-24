package ms.phecda.backend.core.messageaccess.disruptor;

import lombok.Data;
import ms.phecda.backend.core.messageaccess.model.ReadPropertyMessage;

@Data
public class ReportPropertyEvent {
    private ReadPropertyMessage message;
}
