package ms.triones.backend.core.mqtt;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.triones.backend.core.modules.device.service.impl.DeviceService;
import ms.triones.backend.core.modules.devicedata.service.impl.DeviceDataService;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Component
public class MqttPhecdaMessageListener implements IMqttMessageListener {
    private final DeviceDataService deviceDataService;

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        log.info("receice message, topic: {}, message: {}", topic, message);
        byte[] payload = message.getPayload();
        PhecdaMessage phecdaMessage = JSONObject.parseObject(new String(payload), PhecdaMessage.class);
        if (Objects.isNull(phecdaMessage)
                || CollectionUtils.isEmpty(phecdaMessage.getSources())) {
            return;
        }

        List<String> measurements = Lists.newArrayList();
        List<String> values = Lists.newArrayList();
        for (Source source : phecdaMessage.getSources()) {
            measurements.add(source.getName());
            values.add(source.getValue());
        }

        deviceDataService.insertRecord(phecdaMessage.getNodeId(), phecdaMessage.getDeviceName(), phecdaMessage.getOrigin(), measurements, values);
    }

    @Data
    public static class PhecdaMessage {
        private String id;
        private String nodeId;
        private String deviceName;
        private String thingModelVersion;
        private String name;
        private Long origin;
        private List<Source> sources;
    }

    @Data
    public static class Source {
        private String name;
        private String value;
    }
}
