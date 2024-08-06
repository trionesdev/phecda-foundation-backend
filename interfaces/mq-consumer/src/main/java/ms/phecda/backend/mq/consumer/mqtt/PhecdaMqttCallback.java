package ms.phecda.backend.mq.consumer.mqtt;

import com.trionesdev.commons.core.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.internal.disruptor.MessageType;
import ms.phecda.backend.core.internal.disruptor.propertiespost.PropertiesPostEventProducer;
import ms.phecda.backend.core.internal.disruptor.propertiespost.PropertiesPostMessage;
import ms.phecda.backend.core.internal.util.MqttTopicUtils;
import ms.phecda.backend.core.internal.util.TopicUtils;
import ms.phecda.backend.mq.consumer.mqtt.model.MqttPropertiesPostMessage;
import ms.phecda.infrastructure.conf.mqtt.PhecdaMqtt;
import ms.phecda.infrastructure.conf.mqtt.PhecdaMqttProperties;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;


@Slf4j
@RequiredArgsConstructor
@Component
public class PhecdaMqttCallback implements MqttCallbackExtended {
    private final PhecdaMqttProperties mqttProperties;
    private final PhecdaMqtt phecdaMqtt;
    private final PropertiesPostEventProducer propertiesPostEventProducer;


    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        if (log.isInfoEnabled()) {
            log.info("[PhecdaMqttCallback] mqtt connect success is reconnect :{}", reconnect);
        }
        phecdaMqtt.subscribe(TopicUtils.join(mqttProperties.getTopicPrefix(), TopicUtils.propertyPostTopic(null, null)), 1);
        phecdaMqtt.subscribe(TopicUtils.join(mqttProperties.getTopicPrefix(), TopicUtils.eventPostTopic(null, null)), 1);
    }

    @Override
    public void connectionLost(Throwable cause) {
        log.error(cause.getMessage(), cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String propertyPostTopic = TopicUtils.join(mqttProperties.getTopicPrefix(), TopicUtils.propertyPostTopic(null, null));
        if (MqttTopicUtils.isMatched(propertyPostTopic, topic)) { // 设备上报属性消息
            MqttPropertiesPostMessage mqttMessage = JsonUtils.parse(message.getPayload(), MqttPropertiesPostMessage.class);
            PropertiesPostMessage propertiesPostMessage = mqttMessage.toProcessMessage();
            propertiesPostMessage.setType(MessageType.PROPERTIES_POST.name());
            propertiesPostEventProducer.sender(topic, propertiesPostMessage);
        }

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

}
