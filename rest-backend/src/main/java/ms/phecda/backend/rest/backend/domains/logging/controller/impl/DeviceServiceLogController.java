package ms.phecda.backend.rest.backend.domains.logging.controller.impl;

import com.moensun.commons.core.page.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.logging.dao.criteria.DeviceServiceLogCriteria;
import ms.phecda.backend.core.domains.logging.dao.entity.DeviceServiceLog;
import ms.phecda.backend.core.domains.logging.service.impl.DeviceServiceLogService;
import ms.phecda.backend.rest.backend.domains.logging.controller.query.DeviceServiceLogQuery;
import ms.phecda.backend.rest.backend.domains.logging.support.DeviceServiceLogConvertMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ms.phecda.backend.rest.backend.domains.logging.support.LoggingConstants.LOGGING_URI;

@Tag(name = "设备服务日志")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = LOGGING_URI)
public class DeviceServiceLogController {
    private final DeviceServiceLogService deviceServiceLogService;

    @Operation(summary = "设备服务日志分页查询")
    @GetMapping(value = "device-service/page")
    public PageInfo<DeviceServiceLog> page(DeviceServiceLogQuery args) {
        DeviceServiceLogCriteria criteria = DeviceServiceLogConvertMapper.INSTANCE.from(args);
        return deviceServiceLogService.page(criteria);
    }
}
