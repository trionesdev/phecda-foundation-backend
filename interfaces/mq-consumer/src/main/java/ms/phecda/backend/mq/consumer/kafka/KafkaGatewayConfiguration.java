package ms.phecda.backend.mq.consumer.kafka;

import com.trionesdev.commons.core.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.trionesdev.phecda.foundation.core.internal.disruptor.MessageType;
import com.trionesdev.phecda.foundation.core.internal.disruptor.propertiespost.PropertiesPostEventProducer;
import com.trionesdev.phecda.foundation.core.internal.disruptor.propertiespost.PropertiesPostMessage;
import com.trionesdev.phecda.foundation.core.internal.util.TopicUtils;
import ms.phecda.backend.mq.consumer.kafka.model.GatewayPropertiesPostMessage;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class KafkaGatewayConfiguration {
    private final PropertiesPostEventProducer propertiesPostEventProducer;

    @KafkaListener(topics = "phecda-properties-post")
    public void propertiesPost(byte[] message) {
        GatewayPropertiesPostMessage postMessage = JsonUtils.parse(message, GatewayPropertiesPostMessage.class);
        PropertiesPostMessage propertiesPostMessage = postMessage.toProcessMessage();
        propertiesPostMessage.setType(MessageType.PROPERTIES_POST.name());
        propertiesPostEventProducer.sender(TopicUtils.propertyPostTopic(postMessage.getProductKey(), postMessage.getDeviceName()), propertiesPostMessage);
    }

    @KafkaListener(topics = "phecda-events-post")
    public void eventsPost(byte[] message) {
        GatewayPropertiesPostMessage postMessage = JsonUtils.parse(message, GatewayPropertiesPostMessage.class);
    }

}
