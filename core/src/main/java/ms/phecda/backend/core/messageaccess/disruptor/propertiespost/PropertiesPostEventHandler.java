package ms.phecda.backend.core.messageaccess.disruptor.propertiespost;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lmax.disruptor.EventHandler;
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
import ms.phecda.backend.core.domains.device.thing.valuetype.ValueTypeEnum;
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
public class PropertiesPostEventHandler implements EventHandler<PropertiesPostEvent> {
    private final LinkageSceneService linkageSceneService;
    private final ProductService productService;
    private final DeviceService deviceService;
    private final DeviceDataService deviceDataService;


    @Override
    public void onEvent(PropertiesPostEvent event, long l, boolean b) {
        if (log.isDebugEnabled()) {
            log.debug("[PropertiesPostEventHandler#onEvent]  {} {}", JSON.toJSONString(event.getMessage()), l);
        }
        ruleFire(event.getMessage());

        saveMessage(event.getMessage());
    }


    /**
     * 触发联动规则
     *
     * @param message
     */
    public void ruleFire(PropertiesPostMessage message) {
        try {
            Facts facts = new Facts();
            facts.put("productKey", Optional.ofNullable(message.getProductKey()).orElse("nil"));
            facts.put("deviceName", Optional.ofNullable(message.getDeviceName()).orElse("nil"));
            if (MapUtil.isNotEmpty(message.getReadings())) {
                message.getReadings().forEach((k, v) -> {
                    facts.put(k, expressionConvert(v));
                });
            }
            linkageSceneService.rulesFire(facts);
        } catch (Exception ex) {
            log.error("[ReportPropertyEventHandler] rule fire fail: productKey :{} , message: {}", message.getProductKey(), ex.getMessage(), ex);
        }
    }

    private Object expressionConvert(Object val) {
        if (Objects.isNull(val)) {
            return "nil";
        } else {
            return val;
        }
    }

    public void saveMessage(PropertiesPostMessage message) {
        try {
            Product product = productService.findProductByKey(message.getProductKey()).orElse(null);
            if (Objects.isNull(product)) {
                log.warn("product {} not exist", message.getProductKey());
                return;
            }

            Optional<Device> deviceOptional = deviceService.queryByName(message.getDeviceName());
            if (deviceOptional.isEmpty()) {
                log.warn("device {} not exist", message.getDeviceName());
                return;
            }

            Optional<ProductThingModelVersion> thingModelVersionOptional = productService
                    .queryThingModelCache(deviceOptional.get().getProductId(), product.getThingModelVersion());
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

            for (Entry<String, PropertiesPostMessage.Reading> reading : message.getReadings().entrySet()) {
                String identifier = reading.getKey();
                PropertiesPostMessage.Reading property = reading.getValue();
                ValueTypeEnum valueType = identifierAndTypeMap.get(identifier);
                TSDataType tsDataType = IotDbUtils.typeConvert(valueType);
                if (Objects.isNull(tsDataType)) {
                    log.warn("can not convert dataType {} of device {} not exist", valueType, message.getDeviceName());
                    continue;
                }

                List<TSDataType> types = typesMap.get(property.getTs());
                if (Objects.isNull(types)) {
                    types = Lists.newArrayList();
                    typesMap.put(property.getTs(), types);
                }

                List<String> measurements = measurementsMap.get(property.getTs());
                if (Objects.isNull(measurements)) {
                    measurements = Lists.newArrayList();
                    measurementsMap.put(property.getTs(), measurements);
                }

                List<Object> values = valuesMap.get(property.getTs());
                if (Objects.isNull(values)) {
                    values = Lists.newArrayList();
                    valuesMap.put(property.getTs(), values);
                }

                types.add(tsDataType);
                measurements.add(identifier);
                values.add(IotDbUtils.valueConvert(valueType, reading.getValue()));
            }

            for (Entry<Long, List<TSDataType>> entry : typesMap.entrySet()) {
                deviceDataService.insertRecord(product.getId(), message.getDeviceName(),
                        entry.getKey(), measurementsMap.get(entry.getKey()), entry.getValue(),
                        valuesMap.get(entry.getKey()));
            }
        } catch (Exception e) {
            log.error("[ReportPropertyEventHandler] save data fail: productKey :{} , message: {}", message.getProductKey(), e.getMessage(), e);
        }
    }
}
