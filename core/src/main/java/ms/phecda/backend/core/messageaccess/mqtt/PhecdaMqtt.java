package ms.phecda.backend.core.messageaccess.mqtt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Component;

import static ms.phecda.backend.core.messageaccess.constant.TopicConstants.DEVICE_THING_PROPERTY_POST_WILDCARD_TOPIC;
import static ms.phecda.backend.core.messageaccess.constant.TopicConstants.DEVICE_THING_SERVICE_REPLY_WILDCARD_TOPIC;

@Slf4j
@RequiredArgsConstructor
@Component
public class PhecdaMqtt {
    private final IMqttAsyncClient mqttAsyncClient;

    public void subscribe() {
        try {
            mqttAsyncClient.subscribe(DEVICE_THING_PROPERTY_POST_WILDCARD_TOPIC, 1);
            mqttAsyncClient.subscribe(DEVICE_THING_SERVICE_REPLY_WILDCARD_TOPIC, 1);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
