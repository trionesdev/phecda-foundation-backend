package ms.triones.backend.core.messageaccess.mqtt.listener;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeviceEventMessageListener implements IMqttMessageListener  {
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        log.debug("receice message, topic: {}, message: {}", topic, message);
        //todo
    }
}
