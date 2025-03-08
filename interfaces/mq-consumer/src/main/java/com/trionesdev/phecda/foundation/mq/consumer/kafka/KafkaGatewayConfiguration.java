package com.trionesdev.phecda.foundation.mq.consumer.kafka;

import com.trionesdev.commons.core.util.JsonUtils;
import com.trionesdev.phecda.foundation.core.internal.disruptor.message.PhecdaMessageEventProducer;
import com.trionesdev.phecda.model.device.PhecdaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.trionesdev.phecda.foundation.core.internal.util.TopicUtils;
import com.trionesdev.phecda.foundation.mq.consumer.kafka.model.GatewayPropertiesPostMessage;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class KafkaGatewayConfiguration {
//    private final PropertiesPostEventProducer propertiesPostEventProducer;
    private final PhecdaMessageEventProducer phecdaMessageEventProducer;

    @KafkaListener(topics = "phecda-property-post")
    public void propertiesPost(
            @Header("productKey") String productKey,
            @Header("deviceName") String deviceName,
            byte[] message
    ) {
//        GatewayPropertiesPostMessage postMessage = JsonUtils.parse(message, GatewayPropertiesPostMessage.class);
//        PropertiesPostMessage propertiesPostMessage = postMessage.toProcessMessage();
//        propertiesPostMessage.setType(MessageType.PROPERTIES_POST.name());
//        propertiesPostEventProducer.sender(TopicUtils.propertyPostTopic(postMessage.getProductKey(), postMessage.getDeviceName()), propertiesPostMessage);
        phecdaMessageEventProducer.sender(TopicUtils.propertyPostTopic(productKey, deviceName), JsonUtils.parse(message, PhecdaMessage.class));
    }

    @KafkaListener(topics = "phecda-event-post")
    public void eventsPost(byte[] message) {
        GatewayPropertiesPostMessage postMessage = JsonUtils.parse(message, GatewayPropertiesPostMessage.class);
    }

}
