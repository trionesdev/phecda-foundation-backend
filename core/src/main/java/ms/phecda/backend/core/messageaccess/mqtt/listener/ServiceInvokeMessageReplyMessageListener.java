package ms.phecda.backend.core.messageaccess.mqtt.listener;

import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.messageaccess.event.ServiceInvokeReplyEvent;
import ms.phecda.backend.core.messageaccess.model.ServiceInvokeMessageReply;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Component
public class ServiceInvokeMessageReplyMessageListener implements IMqttMessageListener {
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

    }

//    @Override
//    public void messageArrived(String topic, MqttMessage message) throws Exception {
//        byte[] payload = message.getPayload();
//        ServiceInvokeMessageReply phecdaMessage = JSON
//                .parseObject(new String(payload), ServiceInvokeMessageReply.class);
//        if (Objects.isNull(phecdaMessage)) {
//            return;
//        }
//
//        eventPublisher.publishEvent(ServiceInvokeReplyEvent.build(phecdaMessage));
//    }

}
