package ms.phecda.backend.core.messageaccess.mqtt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.messageaccess.mqtt.listener.ReportPropertyMessageListener;
import ms.phecda.backend.core.messageaccess.mqtt.listener.ServiceInvokeMessageReplyMessageListener;
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
    private final ReportPropertyMessageListener reportPropertyMessageListener;
    private final ServiceInvokeMessageReplyMessageListener serviceInvokeMessageReplyMessageListener;

    private static final String SHARE_PREFIX = "";
    private static final String DEVICE_THING_PROPERTY_POST_TOPIC = SHARE_PREFIX + "phecda/+/+/thing/property/post";
    private static final String DEVICE_THING_EVENT_POST_TOPIC = SHARE_PREFIX + "phecda/+/+/thing/event/+/post";
    private static final String DEVICE_THING_SERVICE_REPLY_TOPIC = SHARE_PREFIX + "phecda/{productId}/{deviceName}/thing/service/{identifier}/reply";


    public void subscribe() {
        try {
            mqttAsyncClient.subscribe(DEVICE_THING_PROPERTY_POST_WILDCARD_TOPIC, 1, reportPropertyMessageListener);
            mqttAsyncClient.subscribe(DEVICE_THING_SERVICE_REPLY_WILDCARD_TOPIC, 1, serviceInvokeMessageReplyMessageListener);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
