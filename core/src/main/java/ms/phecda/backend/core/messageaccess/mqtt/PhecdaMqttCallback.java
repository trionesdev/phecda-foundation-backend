package ms.phecda.backend.core.messageaccess.mqtt;

import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.bootstrap.message.process.MessageProcess;
import ms.phecda.backend.core.domains.device.dao.entity.Device;
import ms.phecda.backend.core.domains.device.service.impl.DeviceService;
import ms.phecda.backend.core.messageaccess.disruptor.propertiespost.PropertiesPostMessage;
import ms.phecda.backend.core.messageaccess.mqtt.model.MqttPropertiesPostMessage;
import ms.phecda.backend.core.support.util.MqttTopicUtils;
import ms.phecda.backend.core.support.util.TopicUtils;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Slf4j
@RequiredArgsConstructor
@Component
public class PhecdaMqttCallback implements MqttCallbackExtended {
    private final PhecdaMqttProperties mqttProperties;
    private final PhecdaMqtt phecdaMqtt;
    private final MessageProcess messageProcess;
    private final DeviceService deviceService;


    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        if (log.isInfoEnabled()) {
            log.info("[PhecdaMqttCallback] mqtt connect success is reconnect :{}", reconnect);
        }
        phecdaMqtt.subscribe();
    }

    @Override
    public void connectionLost(Throwable cause) {
        log.error(cause.getMessage(), cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String propertyPostTopic = TopicUtils.join(mqttProperties.getTopicPrefix(), TopicUtils.propertyPostTopic(null, null));
        if (MqttTopicUtils.isMatched(propertyPostTopic, topic)) { // 设备上报属性消息
            MqttPropertiesPostMessage mqttMessage = JSON.parseObject(message.getPayload(), MqttPropertiesPostMessage.class);
            Device device = deviceService.queryByNameCache(mqttMessage.getDeviceName());
            if (Objects.isNull(device)) {
                log.warn("[MessageProcess#propertiesPost] device {} not found ", mqttMessage.getDeviceName());
                return;
            }
            PropertiesPostMessage propertiesPostMessage = mqttMessage.toProcessMessage();
            propertiesPostMessage.setProductId(device.getProductId());
            propertiesPostMessage.setDeviceId(device.getId());
            messageProcess.propertiesPost(TopicUtils.removePrefix(topic, mqttProperties.getTopicPrefix()), propertiesPostMessage);
        }

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

}
