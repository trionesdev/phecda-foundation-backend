package ms.triones.backend.core.messageaccess.event;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.triones.backend.core.messageaccess.model.ReadPropertyMessage;
import ms.triones.backend.core.messageaccess.model.ReadPropertyMessage.Reading;
import ms.triones.backend.core.modules.device.dao.entity.Device;
import ms.triones.backend.core.modules.device.service.impl.DeviceService;
import ms.triones.backend.core.modules.devicedata.service.impl.DeviceDataService;
import ms.triones.backend.core.modules.linkage.service.impl.LinkageSceneService;
import org.jeasy.rules.api.Facts;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Component
public class DevicePropertyHandler {
    private final DeviceDataService deviceDataService;
    private final LinkageSceneService linkageSceneService;
    private final DeviceService deviceService;

    @EventListener
    public void save(DevicePropertyPostEvent<ReadPropertyMessage> event) {
        ReadPropertyMessage message = (ReadPropertyMessage) event.getSource();
        if (Objects.isNull(message)) {
            return;
        }

        List<String> measurements = Lists.newArrayList();
        List<String> values = Lists.newArrayList();
        for (Reading reading : message.getReadings()) {
            measurements.add(reading.getIdentifier());
            values.add(String.valueOf(reading.getValue()));
        }

        deviceDataService.insertRecord(message.getDeviceName(), message.getTimestamp(), measurements, values);
    }

    @EventListener
    public void rulesFire(DevicePropertyPostEvent<ReadPropertyMessage> event) {
        ReadPropertyMessage message = (ReadPropertyMessage) event.getSource();
        if (Objects.isNull(message)) {
            return;
        }

        Optional<Device> deviceOptional = deviceService.queryByName(message.getDeviceName());
        if (!deviceOptional.isPresent()) {
            return;
        }
        Device device = deviceOptional.get();

        Facts facts = new Facts();
        facts.put("product", device.getProductId());
        facts.put("deviceName", message.getDeviceName());
        for (Reading reading : message.getReadings()) {
            facts.put(reading.getIdentifier(), reading.getValue());
        }

        linkageSceneService.rulesFire(facts);
    }
}
