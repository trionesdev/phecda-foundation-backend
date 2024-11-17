package com.trionesdev.phecda.foundation.core.internal.disruptor.propertiespost;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lmax.disruptor.EventHandler;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.DevicePO;
import com.trionesdev.phecda.foundation.core.domains.device.internal.util.IotDbUtils;
import com.trionesdev.phecda.foundation.core.domains.device.service.impl.DeviceDataService;
import com.trionesdev.phecda.foundation.core.domains.device.service.impl.DeviceService;
import com.trionesdev.phecda.foundation.core.domains.device.service.impl.ProductService;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.ActionArgs;
import com.trionesdev.phecda.foundation.core.domains.linkage.service.impl.LinkageSceneService;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.impl.MessageForwardingRuleService;
import com.trionesdev.phecda.model.device.thing.ThingModelProperty;
import com.trionesdev.phecda.model.device.thing.valuetype.ValueTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.jeasy.rules.api.Facts;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

import static com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.RuleConstants.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class PropertiesPostEventHandler implements EventHandler<PropertiesPostEvent> {
    private final MessageForwardingRuleService messageForwardingRuleService;
    private final LinkageSceneService linkageSceneService;
    private final ProductService productService;
    private final DeviceService deviceService;
    private final DeviceDataService deviceDataService;


    @Override
    public void onEvent(PropertiesPostEvent event, long l, boolean b) {
        try {
            deviceDataService.messageRecord();
            PropertiesPostMessage message = event.getMessage();
            DevicePO device = deviceService.queryByNameCache(message.getDeviceName());
            if (Objects.isNull(device)) {
                log.warn("[MessageProcess#propertiesPost] device {} not found ", message.getDeviceName());
                return;
            }

            message.setProductId(device.getProductId());
            message.setDeviceId(device.getId());

            if (log.isDebugEnabled()) {
                log.debug("[PropertiesPostEventHandler#onEvent]  {} {}", JSON.toJSONString(event.getMessage()), l);
            }
            Facts facts = createFacts(message);
            // message forwarding 消息转发处理
            messageForwardingRuleService.fireForwards(facts, message);
            // file rule 规则处理
            linkageSceneService.fireScenes(facts, message);
            // save data
            saveMessage(event.getMessage());
        } catch (Exception ex) {
            log.error("[ReportPropertyEventHandler] process error {} ", ex.getMessage(), ex);
        }
    }

    public Facts createFacts(PropertiesPostMessage message) {
        Facts facts = new Facts();
        facts.put(FACT_PRODUCT_KEY, Optional.ofNullable(message.getProductKey()).orElse("nil"));
        facts.put(FACT_DEVICE_NAME, Optional.ofNullable(message.getDeviceName()).orElse("nil"));
        if (MapUtil.isNotEmpty(message.getReadings())) {
            Map<String, ActionArgs.Reading> readings = Maps.newHashMap();
            message.getReadings().forEach((k, v) -> {
                facts.put(k, Optional.ofNullable(v.getReadingValue()).orElse("nil"));
                readings.put(k, ActionArgs.Reading.builder().identifier(k).value(v.getReadingValue()).build());
            });
            facts.put(FACT_READINGS, readings);
        }
        return facts;
    }


    public void saveMessage(PropertiesPostMessage message) {
        try {
            var product = productService.findProductByKey(message.getProductKey()).orElse(null);
            if (Objects.isNull(product)) {
                log.warn("product {} not exist", message.getProductKey());
                return;
            }

            Optional<DevicePO> deviceOptional = deviceService.queryByName(message.getDeviceName());
            if (deviceOptional.isEmpty()) {
                log.warn("device {} not exist", message.getDeviceName());
                return;
            }


            Map<Long, List<TSDataType>> typesMap = Maps.newHashMap();
            Map<Long, List<String>> measurementsMap = Maps.newHashMap();
            Map<Long, List<Object>> valuesMap = Maps.newHashMap();

            for (Entry<String, PropertiesPostMessage.Reading> reading : message.getReadings().entrySet()) {
                String identifier = reading.getKey();
                PropertiesPostMessage.Reading property = reading.getValue();
//                ValueTypeEnum valueType = identifierAndTypeMap.get(identifier);
                ValueTypeEnum valueType = productService.queryThingModelLatestProperty(product.getKey(), identifier).map(ThingModelProperty::getValueType).orElse(null);
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
                values.add(IotDbUtils.valueConvert(valueType, property.getReadingValue()));
            }

            for (Entry<Long, List<TSDataType>> entry : typesMap.entrySet()) {
                deviceDataService.savePropertyData(product.getKey(), message.getDeviceName(), entry.getKey(), measurementsMap.get(entry.getKey()), entry.getValue(), valuesMap.get(entry.getKey()));
            }
        } catch (Exception e) {
            log.error("[ReportPropertyEventHandler] save data fail: productKey :{} , message: {}", message.getProductKey(), e.getMessage(), e);
        }
    }
}
