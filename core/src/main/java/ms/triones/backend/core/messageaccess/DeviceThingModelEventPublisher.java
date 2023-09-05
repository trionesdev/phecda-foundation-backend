package ms.triones.backend.core.messageaccess;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.triones.backend.core.messageaccess.model.ServiceInvokeMessage;
import ms.triones.backend.core.messageaccess.model.WritePropertyMessage;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class DeviceThingModelEventPublisher {
    private static final String DEVICE_THING_PROPERTY_POST_REPLY = "phecda/{productId}/{deviceName}/thing/property/post_reply";
    private static final String DEVICE_THING_SERVICE_PROPERTY_SET = "phecda/{productId}/{deviceName}/thing/service/property/set";
    private static final String DEVICE_THING_EVENT_POST_REPLY = "phecda/{productId}/{deviceName}/thing/event/{identifier}/post_reply";
    private static final String DEVICE_THING_SERVICE = "phecda/{productId}/{deviceName}/thing/service/{identifier}";

    private final IMqttAsyncClient mqttAsyncClient;
    private final ObjectMapper objectMapper;

    // 同步
    public void syncPublishServiceEvent(ServiceInvokeMessage message) {
        String topic = DEVICE_THING_SERVICE
                .replaceAll("\\{productId}", message.getProductId())
                .replaceAll("\\{deviceName}", message.getDeviceName())
                .replaceAll("\\{identifier}", message.getIdentifier());

        if (StringUtils.isBlank(message.getMessageId())) {
            message.setMessageId(UUID.randomUUID().toString());
        }

        try {
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(objectMapper.writeValueAsBytes(message));
            mqttAsyncClient.publish(topic, mqttMessage);
        } catch (Exception e) {
            log.error("publish service event fail: ", e);
        }
    }

    // 异步
    public void asyncPublishServiceEvent(ServiceInvokeMessage message) {
        String topic = DEVICE_THING_SERVICE
                .replaceAll("\\{productId}", message.getProductId())
                .replaceAll("\\{deviceName}", message.getDeviceName())
                .replaceAll("\\{identifier}", message.getIdentifier());

        if (StringUtils.isBlank(message.getMessageId())) {
            message.setMessageId(UUID.randomUUID().toString());
        }

        try {
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(objectMapper.writeValueAsBytes(message));
            mqttAsyncClient.publish(topic, mqttMessage);
        } catch (Exception e) {
            log.error("publish service event fail: ", e);
        }
    }

    // 同步
    public void syncPublishServicePropertySetEvent(WritePropertyMessage message) {
        String topic = DEVICE_THING_SERVICE_PROPERTY_SET
                .replaceAll("\\{productId}", message.getProductId())
                .replaceAll("\\{deviceName}", message.getDeviceName());

        if (StringUtils.isBlank(message.getMessageId())) {
            message.setMessageId(UUID.randomUUID().toString());
        }

        try {
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(objectMapper.writeValueAsBytes(message));
            mqttAsyncClient.publish(topic, mqttMessage);
        } catch (Exception e) {
            log.error("publish service property set event fail: ", e);
        }
    }

    //异步
    public void asyncPublishServicePropertySetEvent(WritePropertyMessage message) {
        String topic = DEVICE_THING_SERVICE_PROPERTY_SET
                .replaceAll("\\{productId}", message.getProductId())
                .replaceAll("\\{deviceName}", message.getDeviceName());

        if (StringUtils.isBlank(message.getMessageId())) {
            message.setMessageId(UUID.randomUUID().toString());
        }

        try {
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(objectMapper.writeValueAsBytes(message));
            mqttAsyncClient.publish(topic, mqttMessage);
        } catch (Exception e) {
            log.error("publish service property set event fail: ", e);
        }
    }
}
