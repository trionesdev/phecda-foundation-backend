package ms.phecda.backend.core.modules.logging.service.impl;

import com.moensun.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.modules.logging.dao.criteria.DeviceEventLogCriteria;
import ms.phecda.backend.core.modules.logging.dao.entity.DeviceEventLog;
import ms.phecda.backend.core.modules.logging.manager.impl.DeviceEventLogManager;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeviceEventLogService {
    private final DeviceEventLogManager deviceEventLogManager;

    public PageInfo<DeviceEventLog> page(DeviceEventLogCriteria criteria) {
        return deviceEventLogManager.page(criteria);
    }
}
