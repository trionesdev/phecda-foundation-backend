package ms.phecda.backend.core.domains.device.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.dao.criteria.DeviceEventLogCriteria;
import ms.phecda.backend.core.domains.device.dao.criteria.DevicePropertyDataCriteria;
import ms.phecda.backend.core.domains.device.dao.criteria.DeviceServiceLogCriteria;
import ms.phecda.backend.core.domains.device.dao.criteria.DeviceStatisticsMessageDailyCriteria;
import ms.phecda.backend.core.domains.device.dao.entity.DeviceEventLog;
import ms.phecda.backend.core.domains.device.dao.entity.DeviceServiceLog;
import ms.phecda.backend.core.domains.device.dao.entity.DeviceStatisticsMessageDaily;
import ms.phecda.backend.core.domains.device.manager.dto.DevicePropertyDataDTO;
import ms.phecda.backend.core.domains.device.manager.impl.DeviceDataManager;
import ms.phecda.backend.core.domains.device.service.bo.DevicePropertiesPostStatisticsBO;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static ms.phecda.backend.core.domains.device.internal.DeviceCacheConstants.PROPERTIES_POST_STATISTICS_PREFIX;

@RequiredArgsConstructor
@Service
public class DeviceDataService {
    private final StringRedisTemplate stringRedisTemplate;
    private final DeviceDataManager deviceDataManager;

    //region event
    public PageInfo<DeviceEventLog> eventLogsPage(DeviceEventLogCriteria criteria) {
        return deviceDataManager.eventLogsPage(criteria);
    }
    //endregion


    //region service
    public PageInfo<DeviceServiceLog> serviceLogsPage(DeviceServiceLogCriteria criteria) {
        return deviceDataManager.serviceLogsPage(criteria);
    }

    public void saveServiceLog(DeviceServiceLog entity) {
        deviceDataManager.saveServiceLog(entity);
    }


    //endregion


    //region property
    public void savePropertyData(String productKey, String deviceName, long time, List<String> measurements, List<TSDataType> types, List<Object> values) {
        deviceDataManager.savePropertyData(productKey, deviceName, time, measurements, types, values);
    }

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


    public void saveStatisticsMessageDaily(DeviceStatisticsMessageDaily record) {
        deviceDataManager.saveStatisticsMessageDaily(record);
    }

    public DevicePropertiesPostStatisticsBO queryDevicePropertiesPostStatistics() {
        DeviceStatisticsMessageDailyCriteria criteriaTotal = DeviceStatisticsMessageDailyCriteria.builder()
                .type(DeviceStatisticsMessageDaily.Type.PROPERTIES_POST.name())
                .build();
        Long total = deviceDataManager.getQuantitySum(criteriaTotal);

        DeviceStatisticsMessageDailyCriteria criteriaMouthTotal = DeviceStatisticsMessageDailyCriteria.builder()
                .type(DeviceStatisticsMessageDaily.Type.PROPERTIES_POST.name())
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
