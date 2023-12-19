package ms.phecda.backend.core.messageaccess.mqtt;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.domains.messageforwarding.service.factory.ForwardingActionFactory;
import ms.phecda.backend.core.messageaccess.constant.MessageType;
import ms.phecda.backend.core.messageaccess.disruptor.ReportPropertyEventProducer;
import ms.phecda.backend.core.messageaccess.event.ServiceInvokeReplyEvent;
import ms.phecda.backend.core.messageaccess.model.BaseDeviceMessage;
import ms.phecda.backend.core.messageaccess.model.ReadPropertyMessage;
import ms.phecda.backend.core.messageaccess.model.ServiceInvokeMessageReply;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static ms.phecda.backend.core.messageaccess.constant.MessageType.INVOKE_SERVICE_REPLY;
import static ms.phecda.backend.core.messageaccess.constant.MessageType.POST_PROPERTY;
import static ms.phecda.backend.core.messageaccess.constant.TopicConstants.TOPIC_BASE_PREFIX;


@Slf4j
@RequiredArgsConstructor
@Component
public class PhecdaMqttCallback implements MqttCallbackExtended {
    private final PhecdaMqtt phecdaMqtt;
    private final ForwardingActionFactory forwardingActionFactory;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ReportPropertyEventProducer reportPropertyEventProducer;


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
        try {
            Optional<? extends BaseDeviceMessage> messageOptional = convertMessage(topic, message);

            messageOptional.ifPresent(i -> {
                if (Objects.equals(i.getMessageType(), INVOKE_SERVICE_REPLY)) {
                    applicationEventPublisher.publishEvent(ServiceInvokeReplyEvent.build(i));
                }
                if (Objects.equals(i.getMessageType(), POST_PROPERTY)) {
                    reportPropertyEventProducer.sender((ReadPropertyMessage) i);
                }

                forwardingActionFactory.messageForwarding(topic, JSONObject.toJSONString(i).getBytes());
            });
        } catch (Exception e) {
            log.error("handle device post message fail, topic: {}, message: {}, reason: ", topic, message, e);
        }
    }

    private Optional<? extends BaseDeviceMessage> convertMessage(String topic, MqttMessage message) {
        try {
            JSONObject jsonObject = JSON.parseObject(message.getPayload());
            if (log.isDebugEnabled()) {
                log.debug("[PhecdaMqttCallback] arrived message topic: {} message: {}", topic, jsonObject);
            }

            Optional<BaseDeviceMessage> messageOptional = MessageType.convertMessage(jsonObject);
            if (messageOptional.isEmpty()) {
                log.warn("unknown message: {}", jsonObject);
                return Optional.empty();
            }

            String standardTopic = topic.substring(topic.indexOf(TOPIC_BASE_PREFIX));
            String[] standardTopicSplitArr = standardTopic.split("/");
            if (standardTopicSplitArr.length < 3) {
                log.warn("unknown topic: {}", topic);
                return Optional.empty();
            }

            messageOptional.ifPresent(i -> {
                i.setProductId(standardTopicSplitArr[1]);
                i.setDeviceName(standardTopicSplitArr[2]);

                if (Objects.equals(i.getMessageType(), INVOKE_SERVICE_REPLY)) {
                    ((ServiceInvokeMessageReply) i).setIdentifier(standardTopicSplitArr[5]);
                }
            });
            return messageOptional;
        } catch (Exception e) {
            log.error("convert device post message fail, topic: {}, message: {}, reason: ", topic, message, e);
        }

        return Optional.empty();
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
