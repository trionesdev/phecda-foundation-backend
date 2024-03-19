package ms.phecda.backend.core.messageaccess.disruptor.propertiespost;

import lombok.Data;
import ms.phecda.backend.core.messageaccess.mqtt.model.MqttPropertiesPostMessage;

@Data
public class PropertiesPostEvent {
    private PropertiesPostMessage message;
}
