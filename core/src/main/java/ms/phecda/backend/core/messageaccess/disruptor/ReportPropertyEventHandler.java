package ms.phecda.backend.core.messageaccess.disruptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lmax.disruptor.EventHandler;
import com.moensun.commons.exception.spring.ex.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.domains.device.dao.entity.Device;
import ms.phecda.backend.core.domains.device.dao.entity.Product;
import ms.phecda.backend.core.domains.device.dao.entity.ProductThingModelVersion;
import ms.phecda.backend.core.domains.device.service.impl.DeviceService;
import ms.phecda.backend.core.domains.device.service.impl.ProductService;
import ms.phecda.backend.core.domains.device.thing.model.ThingModelProperty;
import ms.phecda.backend.core.domains.devicedata.service.impl.DeviceDataService;
import ms.phecda.backend.core.domains.devicedata.support.util.IotDbUtils;
import ms.phecda.backend.core.domains.linkage.service.impl.LinkageSceneService;
import ms.phecda.backend.core.messageaccess.model.ReadPropertyMessage;
import ms.phecda.edge.base.commons.valuetype.ValueTypeEnum;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.jeasy.rules.api.Facts;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class ReportPropertyEventHandler implements EventHandler<ReportPropertyEvent> {
    private final LinkageSceneService linkageSceneService;
    private final ProductService productService;
    private final DeviceService deviceService;
    private final DeviceDataService deviceDataService;


    @Override
    public void onEvent(ReportPropertyEvent event, long l, boolean b) {
        log.info("{} {}", JSON.toJSONString(event.getMessage()), l);
        ruleFire(event.getMessage());
        saveMessage(event.getMessage());
    }


    /**
     * 触发联动规则
     *
     * @param message
     */
    public void ruleFire(ReadPropertyMessage message) {
        try {
            Facts facts = new Facts();
            facts.put("product", productId(message));
            facts.put("deviceName", message.getDeviceName());
            for (ReadPropertyMessage.Reading reading : message.getParams()) {
                facts.put(reading.getIdentifier(), expressionConvert(reading.getValue()));
            }

            linkageSceneService.rulesFire(facts);
        } catch (Exception ex) {
            log.error("[ReportPropertyEventHandler] rule fire fail: productId :{} , message: {}", message.getProductId(), ex.getMessage(), ex);
        }
    }

    private Object expressionConvert(Object val) {
        if (Objects.isNull(val)) {
            return "nil";
        } else {
            return val;
        }
    }

    public void saveMessage(ReadPropertyMessage message) {
        try {
            Optional<Product> productOptional = productService.queryProductById(message.getProductId());
            if (productOptional.isEmpty()) {
                log.warn("product {} not exist", message.getProductId());
                return;
            }

            Optional<Device> deviceOptional = deviceService.queryByName(message.getDeviceName());
            if (deviceOptional.isEmpty()) {
                log.warn("device {} not exist", message.getDeviceName());
                return;
            }

            Optional<ProductThingModelVersion> thingModelVersionOptional = productService
                    .queryThingModelCache(deviceOptional.get().getProductId(), productOptional.get().getThingModelVersion());
            if (thingModelVersionOptional.isEmpty()) {
                log.warn("thingModel of device {} not exist", message.getDeviceName());
                return;
            }

            List<ThingModelProperty> properties = thingModelVersionOptional.get().getThingModel().getProperties();
            Map<String, ValueTypeEnum> identifierAndTypeMap = properties.stream()
                    .collect(Collectors.toMap(ThingModelProperty::getIdentifier, ThingModelProperty::getValueType));

            Map<Long, List<TSDataType>> typesMap = Maps.newHashMap();
            Map<Long, List<String>> measurementsMap = Maps.newHashMap();
            Map<Long, List<Object>> valuesMap = Maps.newHashMap();
            for (ReadPropertyMessage.Reading reading : message.getParams()) {
                ValueTypeEnum valueType = identifierAndTypeMap.get(reading.getIdentifier());
                TSDataType tsDataType = IotDbUtils.typeConvert(valueType);
                if (Objects.isNull(tsDataType)) {
                    log.warn("can not convert dataType {} of device {} not exist", valueType, message.getDeviceName());
                    continue;
                }

                List<TSDataType> types = typesMap.get(reading.getTimestamp());
                if (Objects.isNull(types)) {
                    types = Lists.newArrayList();
                    typesMap.put(reading.getTimestamp(), types);
                }

                List<String> measurements = measurementsMap.get(reading.getTimestamp());
                if (Objects.isNull(measurements)) {
                    measurements = Lists.newArrayList();
                    measurementsMap.put(reading.getTimestamp(), measurements);
                }

                List<Object> values = valuesMap.get(reading.getTimestamp());
                if (Objects.isNull(values)) {
                    values = Lists.newArrayList();
                    valuesMap.put(reading.getTimestamp(), values);
                }

                types.add(tsDataType);
                measurements.add(reading.getIdentifier());
                values.add(IotDbUtils.valueConvert(valueType, reading.getValue()));
            }

            for (Entry<Long, List<TSDataType>> entry : typesMap.entrySet()) {
                deviceDataService.insertRecord(message.getProductId(), message.getDeviceName(),
                        entry.getKey(), measurementsMap.get(entry.getKey()), entry.getValue(),
                        valuesMap.get(entry.getKey()));
            }
        } catch (Exception e) {
            log.error("[ReportPropertyEventHandler] save data fail: productId :{} , message: {}", message.getProductId(), e.getMessage(), e);
        }
    }

    private String productId(ReadPropertyMessage message) {
        if (StrUtil.isNotBlank(message.getProductId())) {
            return message.getProductId();
        }
        Device device = deviceService.queryByNameCache(message.getDeviceName());
        if (Objects.isNull(device)){
            throw new NotFoundException("DEVICE_NOT_FOUND");
        }
        return device.getProductId();
    }
}
