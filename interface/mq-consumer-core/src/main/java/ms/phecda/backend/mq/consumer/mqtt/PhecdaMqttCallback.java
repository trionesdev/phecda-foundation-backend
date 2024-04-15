package ms.phecda.backend.mq.consumer.mqtt;

import com.alibaba.fastjson2.JSON;
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
    //    private final MessageProcess messageProcess;
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
            MqttPropertiesPostMessage mqttMessage = JSON.parseObject(message.getPayload(), MqttPropertiesPostMessage.class);
            PropertiesPostMessage propertiesPostMessage = mqttMessage.toProcessMessage();
            propertiesPostMessage.setType(MessageType.PROPERTIES_POST.name());
            propertiesPostEventProducer.sender(topic, propertiesPostMessage);
        }


//        try {
//            Optional<? extends BaseDeviceMessage> messageOptional = convertMessage(topic, message);
//
//            messageOptional.ifPresent(i -> {
//                if (Objects.equals(i.getMessageType(), INVOKE_SERVICE_REPLY)) {
//                    applicationEventPublisher.publishEvent(ServiceInvokeReplyEvent.build(i));
//                }
//                if (Objects.equals(i.getMessageType(), POST_PROPERTY)) {
//                    reportPropertyEventProducer.sender((ReadPropertyMessage) i);
//                }
//
//                forwardingActionFactory.messageForwarding(topic, JSONObject.toJSONString(i).getBytes());
//            });
//        } catch (Exception e) {
//            log.error("handle device post message fail, topic: {}, message: {}, reason: ", topic, message, e);
//        }
    }

//    private Optional<? extends BaseDeviceMessage> convertMessage(String topic, MqttMessage message) {
//        try {
//            JSONObject jsonObject = JSON.parseObject(message.getPayload());
//            if (log.isDebugEnabled()) {
//                log.debug("[PhecdaMqttCallback] arrived message topic: {} message: {}", topic, jsonObject);
//            }
//
//            Optional<BaseDeviceMessage> messageOptional = MessageType.convertMessage(jsonObject);
//            if (messageOptional.isEmpty()) {
//                log.warn("unknown message: {}", jsonObject);
//                return Optional.empty();
//            }
//
//            String standardTopic = topic.substring(topic.indexOf(TOPIC_BASE_PREFIX));
//            String[] standardTopicSplitArr = standardTopic.split("/");
//            if (standardTopicSplitArr.length < 3) {
//                log.warn("unknown topic: {}", topic);
//                return Optional.empty();
//            }
//
//            messageOptional.ifPresent(i -> {
//                i.setProductId(standardTopicSplitArr[1]);
//                i.setDeviceName(standardTopicSplitArr[2]);
//
//                if (Objects.equals(i.getMessageType(), INVOKE_SERVICE_REPLY)) {
//                    ((ServiceInvokeMessageReply) i).setIdentifier(standardTopicSplitArr[5]);
//                }
//            });
//            return messageOptional;
//        } catch (Exception e) {
//            log.error("convert device post message fail, topic: {}, message: {}, reason: ", topic, message, e);
//        }
//
//        return Optional.empty();
//    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

}
