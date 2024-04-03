package ms.phecda.backend.core.messageaccess.mqtt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.support.util.TopicUtils;
import org.apache.commons.lang3.ThreadUtils;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@EnableConfigurationProperties(PhecdaMqttProperties.class)
@RequiredArgsConstructor
@Component
public class PhecdaMqtt {
    private final PhecdaMqttProperties mqttProperties;
    private final IMqttAsyncClient mqttAsyncClient;

    public void subscribe() {
        try {
            mqttAsyncClient.subscribe(TopicUtils.join(mqttProperties.getTopicPrefix(), TopicUtils.propertyPostTopic(null, null)), 1);
            mqttAsyncClient.subscribe(TopicUtils.join(mqttProperties.getTopicPrefix(), TopicUtils.eventPostTopic(null, null)), 1);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public MqttMessage publishAsync(String topic,String replyTopic, String messageId, byte[] payload) {
        AtomicBoolean finished = new AtomicBoolean(false);
        try {
            AtomicReference<MqttMessage> replyMessage = new AtomicReference<>(new MqttMessage());
            mqttAsyncClient.subscribe(replyTopic, 1, (topic1, message) -> {
                if (replyTopic.equals(topic1)) {
                    replyMessage.set(message);
                    finished.set(true);
                }
            });
            MqttMessage message = new MqttMessage(payload);
            mqttAsyncClient.publish(topic, message);

            Duration timeoutDuration = Duration.ofSeconds(5);
            Instant startTime = Instant.now();

            while (!finished.get() && Duration.between(startTime, Instant.now()).compareTo(timeoutDuration) <= 0) {
                try {
                    ThreadUtils.sleep(Duration.ofMillis(100));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (!finished.get() && Duration.between(startTime, Instant.now()).compareTo(timeoutDuration) > 0) {
                throw new TimeoutException(" topic " + topic + " replay timeout");
            }
            return replyMessage.get();

        } catch (Exception e) {
            log.error("publish message to topic {} failed, message id: {}", topic, messageId, e);
            throw new RuntimeException(e);
        } finally {
            try {
                finished.set(true);
                mqttAsyncClient.unsubscribe(replyTopic);
            } catch (MqttException e) {
                log.error("unsubscribe topic {} failed", replyTopic, e);
            }
        }
    }

    public void publish(String topic, byte[] payload) {
        try {
            MqttMessage message = new MqttMessage(payload);
            mqttAsyncClient.publish(topic, message);
        } catch (MqttException e) {
            log.error("publish message to topic {} failed", topic, e);
        }
    }

}
