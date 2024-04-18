package ms.phecda.backend.core.domains.device.internal;

import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.service.impl.DeviceDataService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ScheduleConfiguration {
    private final DeviceDataService deviceDataService;

    /**
     * 统计记录前一天的消息量
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void devicePropertiesPostStatisticsDaily() {
        deviceDataService.statisticsMessageDaily();
    }

}
