package ms.phecda.backend.core.messageaccess;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.domains.logging.dao.entity.DeviceServiceLog;
import ms.phecda.backend.core.domains.logging.service.impl.DeviceServiceLogService;
import ms.phecda.backend.core.messageaccess.event.ServiceInvokeReplyEvent;
import ms.phecda.backend.core.messageaccess.model.ServiceInvokeMessage;
import ms.phecda.backend.core.messageaccess.model.ServiceInvokeMessageReply;
import ms.phecda.backend.core.messageaccess.model.WritePropertyMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ThreadUtils;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.UUID;

import static ms.phecda.backend.core.messageaccess.constant.TopicConstants.DEVICE_THING_SERVICE;
import static ms.phecda.backend.core.messageaccess.constant.TopicConstants.DEVICE_THING_SERVICE_PROPERTY_SET;

@Slf4j
@RequiredArgsConstructor
@Component
public class DeviceThingModelEventPublisher {
    private static final Map<String, Object> messageCache = Maps.newConcurrentMap();
    private static final Map<String, Object> serviceReplyCache = Maps.newConcurrentMap();

    private final IMqttAsyncClient mqttAsyncClient;
    private final ObjectMapper objectMapper;
    private final DeviceServiceLogService deviceServiceLogService;

    // 同步
    public ServiceInvokeMessageReply syncPublishServiceEvent(ServiceInvokeMessage message) {
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
            mqttMessage.setPayload(JSON.toJSONBytes(message));
            mqttAsyncClient.publish(topic, mqttMessage);
        } catch (Exception e) {
            log.error("publish service event fail: ", e);
            return null;
        }

        try {
            int waitTime = 0;
            while (waitTime <= 10) {
                ServiceInvokeMessageReply messageReply = (ServiceInvokeMessageReply) serviceReplyCache.remove(message.getMessageId());
                if (Objects.nonNull(messageReply)) {
                    return messageReply;
                }
                ThreadUtils.sleep(Duration.ofSeconds(1));
                waitTime++;
            }
        } catch (Exception e) {
            log.error("wait service reply fail: ", e);
        }

        return null;
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
            mqttMessage.setPayload(JSON.toJSONBytes(message));
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
            mqttMessage.setPayload(JSON.toJSONBytes(message));
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
            mqttMessage.setPayload(JSON.toJSONBytes(message));
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

        serviceReplyCache.put(source.getMessageId(), source);

        log.info("request message: {}, response message: {}", message, source);

        DeviceServiceLog log = DeviceServiceLog.builder()
                .productId(message.getProductId())
                .deviceName(message.getDeviceName())
                .serviceIdentifier(message.getIdentifier())
                .serviceName(null)
                .time(Instant.ofEpochMilli(message.getTimestamp()))
                .build();
        if (!CollectionUtils.isEmpty(message.getParams())) {
            log.setInputData(objectMapper.convertValue(message.getParams(), JsonNode.class));
        }
        if (!CollectionUtils.isEmpty(source.getParams())) {
            log.setOutputData(objectMapper.convertValue(source.getParams(), JsonNode.class));
        }

        deviceServiceLogService.save(log);
    }
}
