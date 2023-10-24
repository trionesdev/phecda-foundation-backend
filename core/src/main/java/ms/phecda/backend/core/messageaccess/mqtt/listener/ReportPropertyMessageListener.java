package ms.phecda.backend.core.messageaccess.mqtt.listener;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.messageaccess.disruptor.ReportPropertyEventProducer;
import ms.phecda.backend.core.messageaccess.model.ReadPropertyMessage;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class ReportPropertyMessageListener implements IMqttMessageListener {

    private final ReportPropertyEventProducer reportPropertyEventProducer;


    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        log.info("receice message, topic: {}, message: {}", topic, message);
        byte[] payload = message.getPayload();
        ReadPropertyMessage phecdaMessage = JSON.parseObject(payload, ReadPropertyMessage.class);
        if (Objects.isNull(phecdaMessage) || CollectionUtil.isEmpty(phecdaMessage.getReadings())) {
            return;
        }
        reportPropertyEventProducer.sender(phecdaMessage);
    }
}
