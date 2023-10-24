package ms.phecda.backend.core.messageaccess.event.handler;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.messageaccess.event.DevicePropertyPostEvent;
import ms.phecda.backend.core.messageaccess.model.ReadPropertyMessage;
import ms.phecda.backend.core.messageaccess.model.ReadPropertyMessage.Reading;
import ms.phecda.backend.core.domains.device.dao.entity.Device;
import ms.phecda.backend.core.domains.device.dao.entity.ProductThingModelVersion;
import ms.phecda.backend.core.domains.device.service.impl.DeviceService;
import ms.phecda.backend.core.domains.device.service.impl.ProductService;
import ms.phecda.backend.core.domains.device.thing.model.ThingModelProperty;
import ms.phecda.backend.core.domains.devicedata.service.impl.DeviceDataService;
import ms.phecda.backend.core.domains.linkage.service.impl.LinkageSceneService;
import ms.phecda.edge.base.commons.valuetype.ValueTypeEnum;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.jeasy.rules.api.Facts;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Component
public class DevicePropertyHandler {
    private final DeviceDataService deviceDataService;
    private final LinkageSceneService linkageSceneService;
    private final DeviceService deviceService;
    private final ProductService productService;

    @EventListener
    public void save(DevicePropertyPostEvent<ReadPropertyMessage> event) {
        ReadPropertyMessage message = (ReadPropertyMessage) event.getSource();
        if (Objects.isNull(message)) {
            return;
        }

        try {
            Optional<Device> deviceOptional = deviceService.queryByName(message.getDeviceName());
            if (!deviceOptional.isPresent()) {
                log.warn("device {} not exist", message.getDeviceName());
                return;
            }
            Optional<ProductThingModelVersion> thingModelVersionOptional = productService
                    .queryThingModel(deviceOptional.get().getProductId(), message.getThingModelVersion());
            if (!thingModelVersionOptional.isPresent()) {
                log.warn("thingModel of device {} not exist", message.getDeviceName());
                return;
            }
            List<ThingModelProperty> properties = thingModelVersionOptional.get().getThingModel().getProperties();
            Map<String, ValueTypeEnum> identifierAndTypeMap = properties.stream()
                    .collect(Collectors.toMap(ThingModelProperty::getIdentifier, ThingModelProperty::getValueType));
            if (identifierAndTypeMap == null || identifierAndTypeMap.size() == 0) {
                log.warn("property of thingModel of device {} not exist", message.getDeviceName());
                return;
            }

            List<TSDataType> types = Lists.newArrayList();
            List<String> measurements = Lists.newArrayList();
            List<Object> values = Lists.newArrayList();
            for (Reading reading : message.getReadings()) {
                ValueTypeEnum valueType = identifierAndTypeMap.get(reading.getIdentifier());
                TSDataType tsDataType = convertIoTDBType(valueType);
                if (Objects.isNull(tsDataType)) {
                    log.warn("can not convert dataType {} of device {} not exist", valueType, message.getDeviceName());
                    continue;
                }

                types.add(tsDataType);
                measurements.add(reading.getIdentifier());
                values.add(convertValue(valueType, reading.getValue()));
            }

            deviceDataService.insertRecord(deviceOptional.get().getProductId(), message.getDeviceName(),
                    message.getTimestamp(), measurements, types, values);
        } catch (Exception e) {
            log.error("save device data fail: ", e);
        }
    }

    private TSDataType convertIoTDBType(ValueTypeEnum valueType) {
        if (ValueTypeEnum.INT.equals(valueType)) {
            return TSDataType.INT32;
        }
        if (ValueTypeEnum.FLOAT.equals(valueType)) {
            return TSDataType.FLOAT;
        }
        if (ValueTypeEnum.DOUBLE.equals(valueType)) {
            return TSDataType.DOUBLE;
        }
        if (ValueTypeEnum.BOOL.equals(valueType)) {
            return TSDataType.BOOLEAN;
        }
        return TSDataType.TEXT;
    }

    private Object convertValue(ValueTypeEnum valueType, Object value) {
        if (Objects.isNull(value)) {
            return null;
        }

        if (ValueTypeEnum.INT.equals(valueType)) {
            return Integer.valueOf(value.toString());
        }
        if (ValueTypeEnum.FLOAT.equals(valueType)) {
            return Float.valueOf(value.toString());
        }
        if (ValueTypeEnum.DOUBLE.equals(valueType)) {
            return Double.valueOf(value.toString());
        }
        if (ValueTypeEnum.BOOL.equals(valueType)) {
            return Boolean.valueOf(value.toString());
        }
        return value.toString();
    }

    @EventListener
    public void rulesFire(DevicePropertyPostEvent<ReadPropertyMessage> event) {
        ReadPropertyMessage message = (ReadPropertyMessage) event.getSource();
        if (Objects.isNull(message)) {
            return;
        }

        try {
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
        } catch (Exception e) {
            log.error("rules fire fail: ", e);
        }
    }
}
