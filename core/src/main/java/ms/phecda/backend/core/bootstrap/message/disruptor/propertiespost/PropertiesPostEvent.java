package ms.phecda.backend.core.bootstrap.message.disruptor.propertiespost;

import lombok.Data;

@Data
public class PropertiesPostEvent {
    private String topic;
    private PropertiesPostMessage message;
}
