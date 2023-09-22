package ms.phecda.backend.core.messageaccess.mqtt;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.messageaccess.mqtt.listener.DeviceEventMessageListener;
import ms.phecda.backend.core.messageaccess.mqtt.listener.ReadPropertyMessageListener;
import ms.phecda.backend.core.messageaccess.mqtt.listener.ServiceInvokeMessageReplyMessageListener;
import ms.phecda.backend.core.modules.device.dao.entity.Device;
import ms.phecda.backend.core.modules.device.service.impl.DeviceService;
import ms.phecda.backend.core.messageaccess.event.DeviceDisableEvent;
import ms.phecda.backend.core.messageaccess.event.DeviceEnableEvent;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Component
public class PhecdaMqttManager {
    private static final String DEVICE_THING_PROPERTY_POST_TOPIC = "phecda/{productId}/{deviceName}/thing/property/post";
    private static final String DEVICE_THING_EVENT_POST_TOPIC = "phecda/{productId}/{deviceName}/thing/event/{identifier}/post";
    private static final String DEVICE_THING_SERVICE_REPLY_TOPIC = "phecda/{productId}/{deviceName}/thing/service/{identifier}/reply";

    private final IMqttAsyncClient mqttAsyncClient;
    private final DeviceService deviceService;
    private final ReadPropertyMessageListener readPropertyMessageListener;
    private final DeviceEventMessageListener deviceEventMessageListener;
    private final ServiceInvokeMessageReplyMessageListener serviceInvokeMessageReplyMessageListener;

    /**
     * 项目启动初始化的时候调用，初始化订阅所有已启用设备的topic
     * @throws Exception
     */
    public void init() throws Exception {
        List<Device> devices = deviceService.queryAllDevice();
        for (Device device : devices) {
            if (device.getEnabled()) {
                subscribeDeviceTopic(device);
            }
        }
    }

    public void subscribeDeviceTopic(Device device) throws Exception {
        String propertyPostTopic = DEVICE_THING_PROPERTY_POST_TOPIC
                .replaceAll("\\{productId}", device.getProductId())
                .replaceAll("\\{deviceName}", device.getName());

        String eventPostTopic = DEVICE_THING_EVENT_POST_TOPIC
                .replaceAll("\\{productId}", device.getProductId())
                .replaceAll("\\{deviceName}", device.getName())
                .replaceAll("\\{identifier}", "+");

        String serviceReplyTopic = DEVICE_THING_SERVICE_REPLY_TOPIC
                .replaceAll("\\{productId}", device.getProductId())
                .replaceAll("\\{deviceName}", device.getName())
                .replaceAll("\\{identifier}", "+");

        mqttAsyncClient.subscribe(propertyPostTopic, 1, readPropertyMessageListener);
        mqttAsyncClient.subscribe(eventPostTopic, 1, deviceEventMessageListener);
        mqttAsyncClient.subscribe(serviceReplyTopic, 1, serviceInvokeMessageReplyMessageListener);

        log.info("device[{}] subscribe topic: {}", device.getName(), propertyPostTopic);
        log.info("device[{}] subscribe topic: {}", device.getName(), eventPostTopic);
        log.info("device[{}] subscribe topic: {}", device.getName(), serviceReplyTopic);
    }

    public void unsubscribeDeviceTopic(Device device) throws Exception {
        String propertyPostTopic = DEVICE_THING_PROPERTY_POST_TOPIC
                .replaceAll("\\{productId}", device.getProductId())
                .replaceAll("\\{deviceName}", device.getName());

        String eventPostTopic = DEVICE_THING_EVENT_POST_TOPIC
                .replaceAll("\\{productId}", device.getProductId())
                .replaceAll("\\{deviceName}", device.getName())
                .replaceAll("\\{identifier}", "+");

        String serviceReplyTopic = DEVICE_THING_SERVICE_REPLY_TOPIC
                .replaceAll("\\{productId}", device.getProductId())
                .replaceAll("\\{deviceName}", device.getName())
                .replaceAll("\\{identifier}", "+");

        List<String> topics = Lists.newArrayList(propertyPostTopic, eventPostTopic, serviceReplyTopic);

        mqttAsyncClient.unsubscribe(topics.toArray(new String[0]));

        log.info("device[{}] unsubscribe topic: {}", device.getName(), propertyPostTopic);
        log.info("device[{}] unsubscribe topic: {}", device.getName(), eventPostTopic);
        log.info("device[{}] unsubscribe topic: {}", device.getName(), serviceReplyTopic);
    }

    @EventListener
    public void unsubscribeDeviceTopic(DeviceDisableEvent<Device> event) {
        Device source = (Device) event.getSource();
        if (Objects.isNull(source)) {
            return;
        }

        try {
            unsubscribeDeviceTopic(source);
        } catch (Exception e) {
            log.error("unsubscribe device [" + source.getName() + "] topic fail: ", e);
        }
    }

    @EventListener
    public void subscribeDeviceTopic(DeviceEnableEvent<Device> event) {
        Device source = (Device) event.getSource();
        if (Objects.isNull(source)) {
            return;
        }

        try {
            subscribeDeviceTopic(source);
        } catch (Exception e) {
            log.error("subscribe device [" + source.getName() + "] topic fail: ", e);
        }
    }
}
