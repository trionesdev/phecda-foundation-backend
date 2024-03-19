package ms.phecda.backend.core.bootstrap.listeners.kafka;

import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.bootstrap.listeners.kafka.model.GatewayPropertiesPostMessage;
import ms.phecda.backend.core.bootstrap.message.process.MessageProcess;
import ms.phecda.backend.core.domains.device.dao.entity.Device;
import ms.phecda.backend.core.domains.device.service.impl.DeviceService;
import ms.phecda.backend.core.messageaccess.disruptor.propertiespost.PropertiesPostMessage;
import ms.phecda.backend.core.support.util.TopicUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class KafkaGatewayConfiguration {
    private final DeviceService deviceService;
    private final MessageProcess messageProcess;

    @KafkaListener(topics = "phecda-properties-post")
    public void propertiesPost(byte[] message) {
        GatewayPropertiesPostMessage postMessage = JSON.parseObject(message, GatewayPropertiesPostMessage.class);
        Device device = deviceService.queryByNameCache(postMessage.getDeviceName());
        if (Objects.isNull(device)) {
            log.warn("[MessageProcess#propertiesPost] device {} not found ", postMessage.getDeviceName());
            return;
        }
        PropertiesPostMessage propertiesPostMessage = postMessage.toProcessMessage();
        propertiesPostMessage.setProductId(device.getProductId());
        propertiesPostMessage.setDeviceId(device.getId());
        messageProcess.propertiesPost(TopicUtils.propertyPostTopic(postMessage.getProductKey(), postMessage.getDeviceName()), propertiesPostMessage);
    }

    @KafkaListener(topics = "phecda-events-post")
    public void eventsPost(byte[] message) {
        GatewayPropertiesPostMessage postMessage = JSON.parseObject(message, GatewayPropertiesPostMessage.class);
    }

}
