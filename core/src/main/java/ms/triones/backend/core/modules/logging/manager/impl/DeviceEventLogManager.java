package ms.triones.backend.core.modules.logging.manager.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moensun.commons.core.page.PageInfo;
import com.moensun.commons.mybatisplus.util.MpPageUtils;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.logging.dao.criteria.DeviceEventLogCriteria;
import ms.triones.backend.core.modules.logging.dao.entity.DeviceEventLog;
import ms.triones.backend.core.modules.logging.dao.impl.DeviceEventLogDAO;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeviceEventLogManager {
    private final DeviceEventLogDAO deviceEventLogDAO;

    public PageInfo<DeviceEventLog> page(DeviceEventLogCriteria criteria) {
        Page<DeviceEventLog> page = deviceEventLogDAO.page(criteria);

        return MpPageUtils.of(page);
    }
}
