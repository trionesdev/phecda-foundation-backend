package ms.phecda.backend.core.modules.logging.service.impl;

import com.moensun.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.modules.logging.dao.criteria.DeviceServiceLogCriteria;
import ms.phecda.backend.core.modules.logging.dao.entity.DeviceServiceLog;
import ms.phecda.backend.core.modules.logging.manager.impl.DeviceServiceLogManager;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeviceServiceLogService {
    private final DeviceServiceLogManager deviceServiceLogManager;

    public PageInfo<DeviceServiceLog> page(DeviceServiceLogCriteria criteria) {
        return deviceServiceLogManager.page(criteria);
    }

    public void save(DeviceServiceLog entity) {
        deviceServiceLogManager.save(entity);
    }
}
