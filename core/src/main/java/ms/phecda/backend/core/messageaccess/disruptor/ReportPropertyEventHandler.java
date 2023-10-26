package ms.phecda.backend.core.messageaccess.disruptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.lmax.disruptor.EventHandler;
import com.moensun.commons.exception.spring.ex.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.domains.device.dao.entity.Device;
import ms.phecda.backend.core.domains.device.service.impl.DeviceService;
import ms.phecda.backend.core.domains.devicedata.service.impl.DeviceDataService;
import ms.phecda.backend.core.domains.devicedata.support.util.IotDbUtils;
import ms.phecda.backend.core.domains.linkage.service.impl.LinkageSceneService;
import ms.phecda.backend.core.messageaccess.constant.ReadingValueTypeEnum;
import ms.phecda.backend.core.messageaccess.model.ReadPropertyMessage;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.jeasy.rules.api.Facts;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class ReportPropertyEventHandler implements EventHandler<ReportPropertyEvent> {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final LinkageSceneService linkageSceneService;
    private final DeviceService deviceService;
    private final DeviceDataService deviceDataService;

    @Override
    public void onEvent(ReportPropertyEvent event, long l, boolean b) {
        log.info("{} {}", JSON.toJSONString(event.getMessage()), l);
        redirect(event.getMessage());
        ruleFire(event.getMessage());
        saveMessage(event.getMessage());
    }

    /**
     * 转发
     *
     * @param message
     */
    public void redirect(ReadPropertyMessage message) {
        kafkaTemplate.send("device-thing-property", productId(message), JSON.toJSONString(message));
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
            for (ReadPropertyMessage.Reading reading : message.getReadings()) {
                facts.put(reading.getIdentifier(), reading.getValue());
            }

            linkageSceneService.rulesFire(facts);
        } catch (Exception ex) {
            log.error("[ReportPropertyEventHandler] rule fire fail: productId :{} , message: {}", message.getProductId(), ex.getMessage(), ex);
        }
    }

    public void saveMessage(ReadPropertyMessage message) {
        try {
            List<TSDataType> types = Lists.newArrayList();
            List<String> measurements = Lists.newArrayList();
            List<Object> values = Lists.newArrayList();
            for (ReadPropertyMessage.Reading reading : message.getReadings()) {
                ReadingValueTypeEnum readingValueType = ReadingValueTypeEnum.of(reading.getValueType());
                if (Objects.isNull(readingValueType)) {
                    log.warn("can not convert dataType {} of device {} not exist", reading.getValueType(), message.getDeviceName());
                    continue;
                }
                TSDataType tsDataType = IotDbUtils.typeConvert(readingValueType.getValueType());

                types.add(tsDataType);
                measurements.add(reading.getIdentifier());
                values.add(IotDbUtils.valueConvert(readingValueType.getValueType(), reading.getValue()));
            }

            deviceDataService.insertRecord(message.getProductId(), message.getDeviceName(),
                    message.getTimestamp(), measurements, types, values);

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
