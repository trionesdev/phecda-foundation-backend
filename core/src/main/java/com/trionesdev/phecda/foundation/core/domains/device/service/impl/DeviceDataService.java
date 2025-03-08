package com.trionesdev.phecda.foundation.core.domains.device.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.exception.BusinessException;
import com.trionesdev.phecda.foundation.core.domains.device.dto.DevicePropertyDataDTO;
import com.trionesdev.phecda.foundation.core.domains.device.internal.util.IotDbUtils;
import com.trionesdev.phecda.model.device.PhecdaMessage;
import com.trionesdev.phecda.model.device.thing.valuetype.ValueTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.DeviceEventLogCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.DevicePropertyDataCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.DeviceServiceLogCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.DeviceStatisticsMessageDailyCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.DeviceEventLogPO;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.DeviceCommandLogPO;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.DeviceStatisticsMessageDailyPO;
import com.trionesdev.phecda.foundation.core.domains.device.manager.impl.DeviceDataManager;
import com.trionesdev.phecda.foundation.core.domains.device.service.bo.DevicePropertiesPostStatisticsBO;
import org.apache.commons.lang3.EnumUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.trionesdev.phecda.foundation.core.domains.device.internal.DeviceCacheConstants.PROPERTIES_POST_STATISTICS_DAILY_LOCK_KEY;
import static com.trionesdev.phecda.foundation.core.domains.device.internal.DeviceCacheConstants.PROPERTIES_POST_STATISTICS_PREFIX;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeviceDataService {
    private final StringRedisTemplate stringRedisTemplate;
    private final DeviceDataManager deviceDataManager;
    private final RedissonClient redissonClient;

    public void savePropertyData(PhecdaMessage message) {
//        Map<Long, List<TSDataType>> typesMap = Maps.newHashMap();
//        Map<Long, List<String>> measurementsMap = Maps.newHashMap();
//        Map<Long, List<Object>> valuesMap = Maps.newHashMap();
//
//        for (Entry<String, PhecdaMessage.Reading> reading : message.getReadings().entrySet()) {
//            String identifier = reading.getKey();
//            PhecdaMessage.Reading property = reading.getValue();
//            ValueTypeEnum valueType = EnumUtils.getEnum(ValueTypeEnum.class, property.getValueType(), ValueTypeEnum.STRING);
//            TSDataType tsDataType = IotDbUtils.typeConvert(valueType);
//            if (Objects.isNull(tsDataType)) {
//                log.warn("can not convert dataType {} of device {} not exist", valueType, message.getDeviceName());
//                continue;
//            }
//
//            List<TSDataType> types = typesMap.get(property.getTs());
//            if (Objects.isNull(types)) {
//                types = Lists.newArrayList();
//                typesMap.put(property.getTs(), types);
//            }
//
//            List<String> measurements = measurementsMap.get(property.getTs());
//            if (Objects.isNull(measurements)) {
//                measurements = Lists.newArrayList();
//                measurementsMap.put(property.getTs(), measurements);
//            }
//
//            List<Object> values = valuesMap.get(property.getTs());
//            if (Objects.isNull(values)) {
//                values = Lists.newArrayList();
//                valuesMap.put(property.getTs(), values);
//            }
//
//            types.add(tsDataType);
//            measurements.add(identifier);
//            values.add(IotDbUtils.valueConvert(valueType, property.getReadingValue()));
//        }
//
//        for (Entry<Long, List<TSDataType>> entry : typesMap.entrySet()) {
//            deviceDataManager.savePropertyData(message.getProductKey(), message.getDeviceName(), entry.getKey(), measurementsMap.get(entry.getKey()), entry.getValue(), valuesMap.get(entry.getKey()));
//        }
        deviceDataManager.savePropertyData(IotDbUtils.messageToInsert(message));
    }


    //region event
    public PageInfo<DeviceEventLogPO> eventLogsPage(DeviceEventLogCriteria criteria) {
        return deviceDataManager.eventLogsPage(criteria);
    }
    //endregion


    //region service
    public PageInfo<DeviceCommandLogPO> serviceLogsPage(DeviceServiceLogCriteria criteria) {
        return deviceDataManager.serviceLogsPage(criteria);
    }

    public void saveServiceLog(DeviceCommandLogPO entity) {
        deviceDataManager.saveServiceLog(entity);
    }


    //endregion


    //region property

//    @Deprecated
//    public void savePropertyData(String productKey, String deviceName, long time, List<String> measurements, List<TSDataType> types, List<Object> values) {
//        deviceDataManager.savePropertyData(productKey, deviceName, time, measurements, types, values);
//    }

    public List<DevicePropertyDataDTO> queryDevicePropertyDataList(DevicePropertyDataCriteria criteria) {
        return deviceDataManager.queryDevicePropertyDataList(criteria);
    }
    //endregion

    public void messageRecord() {
        LocalDateTime currentDate = LocalDateTime.now();

        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateDate = currentDate.format(formatterDate);

        DateTimeFormatter formatterMinute = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH-mm");
        String formattedDateMinute = currentDate.format(formatterMinute);
        String minuteKey = PROPERTIES_POST_STATISTICS_PREFIX + formattedDateMinute;
        String dateKey = PROPERTIES_POST_STATISTICS_PREFIX + formattedDateDate;

        Long minuteCount = stringRedisTemplate.opsForValue().increment(minuteKey, 1);
        Long hourCount = stringRedisTemplate.opsForValue().increment(dateKey, 1);
        if (Objects.nonNull(minuteCount) && minuteCount <= 1) {
            stringRedisTemplate.expire(minuteKey, 2, TimeUnit.HOURS);
        }
        if (Objects.nonNull(hourCount) && hourCount <= 1) {
            stringRedisTemplate.expire(dateKey, 28, TimeUnit.HOURS);
        }
    }

    /**
     * 按分钟统计消息数量
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public Map<String, Long> messageStatisticsMinutely(Instant startTime, Instant endTime) {
        Duration duration = Duration.between(startTime, endTime);
        if (duration.get(ChronoUnit.HOURS) > 2) {
            throw new BusinessException("TIME_RANGE_TOO_LARGE");
        }
        Set<String> minuteKeys = CollUtil.newHashSet();
        DateTimeFormatter formatterMinute = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH-mm");
        while (startTime.isBefore(endTime)) {
            startTime.plus(1, ChronoUnit.MINUTES);
            minuteKeys.add(formatterMinute.format(startTime));
        }
        Map<String, Long> messagesMap = new HashMap<>();
        minuteKeys.forEach(key -> {
            String count = stringRedisTemplate.opsForValue().get(PROPERTIES_POST_STATISTICS_PREFIX + key);
            messagesMap.put(key, Objects.isNull(count) ? 0L : Long.parseLong(count));
        });
        return messagesMap;
    }

    /**
     * 记录前一天的消息数量，通过分布式锁保障只有一个实例执行
     */
    public void statisticsMessageDaily() {
        LocalDate targetDate = LocalDate.now().minusDays(1);
        List<DeviceStatisticsMessageDailyPO> records = deviceDataManager.findList(DeviceStatisticsMessageDailyCriteria.builder()
                .type(DeviceStatisticsMessageDailyPO.Type.PROPERTIES_POST.name()).date(targetDate).build());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = targetDate.format(formatter);
        if (CollUtil.isNotEmpty(records)) {
            log.warn("[DeviceDataService] day {} message statistics has logged ", formattedDate);
            return;
        }

        RLock lock = redissonClient.getLock(PROPERTIES_POST_STATISTICS_DAILY_LOCK_KEY);
        try {
            boolean isLocked = lock.tryLock(10, 10, TimeUnit.SECONDS);
            if (isLocked) {
                String count = stringRedisTemplate.opsForValue().get(PROPERTIES_POST_STATISTICS_PREFIX + formattedDate);
                DeviceStatisticsMessageDailyPO record = DeviceStatisticsMessageDailyPO.builder()
                        .type(DeviceStatisticsMessageDailyPO.Type.PROPERTIES_POST.name())
                        .date(targetDate)
                        .quantity(Objects.isNull(count) ? 0L : Long.parseLong(count))
                        .build();
                record.setDate(LocalDateTime.now().minusDays(1).toLocalDate());
                deviceDataManager.saveStatisticsMessageDaily(record);
            }
        } catch (InterruptedException ignored) {
            log.error("[DeviceDataService] day {} message statistics lock failed ", formattedDate);
        } finally {
            lock.unlock();
        }
    }

    public DevicePropertiesPostStatisticsBO queryDevicePropertiesPostStatistics() {
        DeviceStatisticsMessageDailyCriteria criteriaTotal = DeviceStatisticsMessageDailyCriteria.builder()
                .type(DeviceStatisticsMessageDailyPO.Type.PROPERTIES_POST.name())
                .build();
        Long total = deviceDataManager.getQuantitySum(criteriaTotal);

        DeviceStatisticsMessageDailyCriteria criteriaMouthTotal = DeviceStatisticsMessageDailyCriteria.builder()
                .type(DeviceStatisticsMessageDailyPO.Type.PROPERTIES_POST.name())
                .startTime(LocalDateTime.now().withDayOfMonth(1).atZone(ZoneId.systemDefault()).toInstant())
                .endTime(Instant.now())
                .build();
        Long mouthTotal = deviceDataManager.getQuantitySum(criteriaMouthTotal);
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateDate = LocalDateTime.now().format(formatterDate);
        String dayTotal = stringRedisTemplate.opsForValue().get(PROPERTIES_POST_STATISTICS_PREFIX + formattedDateDate);
        return DevicePropertiesPostStatisticsBO.builder()
                .total(total)
                .monthTotal(mouthTotal)
                .dayTotal(Objects.isNull(dayTotal) ? 0L : Long.parseLong(dayTotal))
                .build();
    }

}
