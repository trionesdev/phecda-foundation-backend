package ms.phecda.backend.rest.backend.modules.logging.controller.impl;

import com.moensun.commons.core.page.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.modules.logging.dao.criteria.DeviceEventLogCriteria;
import ms.phecda.backend.core.modules.logging.dao.entity.DeviceEventLog;
import ms.phecda.backend.core.modules.logging.service.impl.DeviceEventLogService;
import ms.phecda.backend.rest.backend.modules.logging.controller.query.DeviceEventLogQuery;
import ms.phecda.backend.rest.backend.modules.logging.support.DeviceEventLogConvertMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ms.phecda.backend.rest.backend.modules.logging.support.LoggingConstants.LOGGING_URI;

@Tag(name = "设备事件管理日志")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = LOGGING_URI)
public class DeviceEventLogController {
    private final DeviceEventLogService deviceEventLogService;

    @Operation(summary = "设备事件管理日志分页查询")
    @GetMapping(value = "device-event/page")
    public PageInfo<DeviceEventLog> page(DeviceEventLogQuery args) {
        DeviceEventLogCriteria criteria = DeviceEventLogConvertMapper.INSTANCE.from(args);
        return deviceEventLogService.page(criteria);
    }
}
