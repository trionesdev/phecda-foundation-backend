package ms.phecda.backend.core.modules.logging.manager.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moensun.commons.core.page.PageInfo;
import com.moensun.commons.mybatisplus.util.MpPageUtils;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.modules.logging.dao.criteria.DeviceServiceLogCriteria;
import ms.phecda.backend.core.modules.logging.dao.entity.DeviceServiceLog;
import ms.phecda.backend.core.modules.logging.dao.impl.DeviceServiceLogDAO;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeviceServiceLogManager {
    private final DeviceServiceLogDAO deviceServiceLogDAO;

    public PageInfo<DeviceServiceLog> page(DeviceServiceLogCriteria criteria) {
        Page<DeviceServiceLog> page = deviceServiceLogDAO.page(criteria);

        return MpPageUtils.of(page);
    }

    public void save(DeviceServiceLog entity) {
        deviceServiceLogDAO.save(entity);
    }
}
