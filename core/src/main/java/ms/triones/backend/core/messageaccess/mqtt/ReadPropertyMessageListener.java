package ms.triones.backend.core.messageaccess.mqtt;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import ms.triones.backend.core.messageaccess.event.DevicePropertyPostEvent;
import ms.triones.backend.core.messageaccess.model.ReadPropertyMessage;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@Component
public class ReadPropertyMessageListener implements IMqttMessageListener {
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        log.info("receice message, topic: {}, message: {}", topic, message);
        byte[] payload = message.getPayload();
        ReadPropertyMessage phecdaMessage = JSONObject.parseObject(new String(payload), ReadPropertyMessage.class);
        if (Objects.isNull(phecdaMessage) || CollectionUtils.isEmpty(phecdaMessage.getReadings())) {
            return;
        }

        applicationEventPublisher.publishEvent(DevicePropertyPostEvent.build(phecdaMessage));
    }
}
