package ms.triones.backend.core.messageaccess;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.triones.backend.core.messageaccess.event.ServiceInvokeReplyEvent;
import ms.triones.backend.core.messageaccess.model.ReadPropertyMessage;
import ms.triones.backend.core.messageaccess.model.ServiceInvokeMessage;
import ms.triones.backend.core.messageaccess.model.ServiceInvokeMessageReply;
import ms.triones.backend.core.messageaccess.model.WritePropertyMessage;
import ms.triones.backend.core.modules.logging.dao.entity.DeviceServiceLog;
import ms.triones.backend.core.modules.logging.service.impl.DeviceServiceLogService;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class DeviceThingModelEventPublisher {
    private static final String DEVICE_THING_PROPERTY_POST_REPLY = "phecda/{productId}/{deviceName}/thing/property/post_reply";
    private static final String DEVICE_THING_SERVICE_PROPERTY_SET = "phecda/{productId}/{deviceName}/thing/service/property/set";
    private static final String DEVICE_THING_EVENT_POST_REPLY = "phecda/{productId}/{deviceName}/thing/event/{identifier}/post_reply";
    private static final String DEVICE_THING_SERVICE = "phecda/{productId}/{deviceName}/thing/service/{identifier}";

    private static final Map<String, Object> messageCache = Maps.newConcurrentMap();

    private final IMqttAsyncClient mqttAsyncClient;
    private final ObjectMapper objectMapper;
    private final DeviceServiceLogService deviceServiceLogService;

    // 同步
    public void syncPublishServiceEvent(ServiceInvokeMessage message) {
        String topic = DEVICE_THING_SERVICE
                .replaceAll("\\{productId}", message.getProductId())
                .replaceAll("\\{deviceName}", message.getDeviceName())
                .replaceAll("\\{identifier}", message.getIdentifier());

        if (StringUtils.isBlank(message.getMessageId())) {
            message.setMessageId(UUID.randomUUID().toString());
        }

        synchronized (messageCache) {
            messageCache.put(message.getMessageId(), message);
        }

        try {
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(objectMapper.writeValueAsBytes(message));
            mqttAsyncClient.publish(topic, mqttMessage);
        } catch (Exception e) {
            log.error("publish service event fail: ", e);
        }
    }

    // 异步
    public void asyncPublishServiceEvent(ServiceInvokeMessage message) {
        String topic = DEVICE_THING_SERVICE
                .replaceAll("\\{productId}", message.getProductId())
                .replaceAll("\\{deviceName}", message.getDeviceName())
                .replaceAll("\\{identifier}", message.getIdentifier());

        if (StringUtils.isBlank(message.getMessageId())) {
            message.setMessageId(UUID.randomUUID().toString());
        }

        synchronized (messageCache) {
            messageCache.put(message.getMessageId(), message);
        }

        try {
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(objectMapper.writeValueAsBytes(message));
            mqttAsyncClient.publish(topic, mqttMessage);
        } catch (Exception e) {
            log.error("publish service event fail: ", e);
        }
    }

    // 同步
    public void syncPublishServicePropertySetEvent(WritePropertyMessage message) {
        String topic = DEVICE_THING_SERVICE_PROPERTY_SET
                .replaceAll("\\{productId}", message.getProductId())
                .replaceAll("\\{deviceName}", message.getDeviceName());

        if (StringUtils.isBlank(message.getMessageId())) {
            message.setMessageId(UUID.randomUUID().toString());
        }

        try {
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(objectMapper.writeValueAsBytes(message));
            mqttAsyncClient.publish(topic, mqttMessage);
        } catch (Exception e) {
            log.error("publish service property set event fail: ", e);
        }
    }

    //异步
    public void asyncPublishServicePropertySetEvent(WritePropertyMessage message) {
        String topic = DEVICE_THING_SERVICE_PROPERTY_SET
                .replaceAll("\\{productId}", message.getProductId())
                .replaceAll("\\{deviceName}", message.getDeviceName());

        if (StringUtils.isBlank(message.getMessageId())) {
            message.setMessageId(UUID.randomUUID().toString());
        }

        try {
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(objectMapper.writeValueAsBytes(message));
            mqttAsyncClient.publish(topic, mqttMessage);
        } catch (Exception e) {
            log.error("publish service property set event fail: ", e);
        }
    }

    @Scheduled(fixedDelay = 10 * 1000)
    public void checkMessageTimeout() {
        synchronized (messageCache) {
            Iterator<Entry<String, Object>> iterator = messageCache.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, Object> next = iterator.next();
                if (next.getValue() instanceof ServiceInvokeMessage) {
                    ServiceInvokeMessage message = (ServiceInvokeMessage) next.getValue();
                    if (System.currentTimeMillis() - message.getTimestamp() > 30 * 1000) {
                        iterator.remove();

                        DeviceServiceLog log = DeviceServiceLog.builder()
                                .productId(message.getProductId())
                                .deviceName(message.getDeviceName())
                                .serviceIdentifier(message.getIdentifier())
                                .serviceName(null)
                                .inputData(objectMapper.convertValue(message.getParams(), JsonNode.class))
                                .outputData(null)
                                .time(Instant.ofEpochMilli(message.getTimestamp()))
                                .build();
                        deviceServiceLogService.save(log);
                    }
                }
            }
        }
    }

    @Async
    @EventListener
    public void serviceReplyHandle(ServiceInvokeReplyEvent<ServiceInvokeMessageReply> event) {
        ServiceInvokeMessageReply source = (ServiceInvokeMessageReply) event.getSource();
        if (Objects.isNull(source)) {
            return;
        }

        ServiceInvokeMessage message = null;
        synchronized (messageCache) {
            message = (ServiceInvokeMessage) messageCache.remove(source.getMessageId());
        }

        if (Objects.isNull(message)) {
            log.warn("service invoke message cannot found mapping: {}", source);
            return;
        }

        log.info("request message: {}, response message: {}", message, source);

        DeviceServiceLog log = DeviceServiceLog.builder()
                .productId(message.getProductId())
                .deviceName(message.getDeviceName())
                .serviceIdentifier(message.getIdentifier())
                .serviceName(null)
                .inputData(objectMapper.convertValue(message.getParams(), JsonNode.class))
                .outputData(objectMapper.convertValue(source.getParams(), JsonNode.class))
                .time(Instant.ofEpochMilli(message.getTimestamp()))
                .build();
        deviceServiceLogService.save(log);
    }
}
