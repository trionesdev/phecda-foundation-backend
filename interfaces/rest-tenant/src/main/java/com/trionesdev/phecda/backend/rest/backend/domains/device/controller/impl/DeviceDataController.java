package com.trionesdev.phecda.backend.rest.backend.domains.device.controller.impl;

import com.trionesdev.commons.core.page.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.backend.core.domains.device.dao.criteria.DeviceEventLogCriteria;
import com.trionesdev.phecda.backend.core.domains.device.dao.criteria.DevicePropertyDataCriteria;
import com.trionesdev.phecda.backend.core.domains.device.dao.criteria.DeviceServiceLogCriteria;
import com.trionesdev.phecda.backend.core.domains.device.dao.po.DeviceEventLogPO;
import com.trionesdev.phecda.backend.core.domains.device.dao.po.DeviceServiceLogPO;
import com.trionesdev.phecda.backend.core.domains.device.dto.DevicePropertyDataDTO;
import com.trionesdev.phecda.backend.core.domains.device.service.bo.DevicePropertiesPostStatisticsBO;
import com.trionesdev.phecda.backend.core.domains.device.service.impl.DeviceDataService;
import com.trionesdev.phecda.backend.rest.backend.domains.device.controller.query.DeviceEventLogQuery;
import com.trionesdev.phecda.backend.rest.backend.domains.device.controller.query.DevicePropertyDataQuery;
import com.trionesdev.phecda.backend.rest.backend.domains.device.controller.query.DeviceServiceLogQuery;
import com.trionesdev.phecda.backend.rest.backend.domains.device.internal.DeviceBeRestConvert;
import com.trionesdev.phecda.backend.rest.backend.domains.device.internal.DeviceConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "设备物模型")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = DeviceConstants.DEVICE_URI)
public class DeviceDataController {
    private final DeviceBeRestConvert deviceBeRestConvert;
    private final DeviceDataService deviceDataService;

    @Operation(summary = "查询设备属性数据统计")
    @GetMapping(value = "properties-post/statistics")
    public DevicePropertiesPostStatisticsBO queryDevicePropertiesPostStatistics() {
        return deviceDataService.queryDevicePropertiesPostStatistics();
    }

    @Operation(summary = "查询设备属性数据列表")
    @GetMapping(value = "property/data/list")
    public List<DevicePropertyDataDTO> queryProperties(DevicePropertyDataQuery query) {
        DevicePropertyDataCriteria criteria = deviceBeRestConvert.from(query);
        return deviceDataService.queryDevicePropertyDataList(criteria);
    }

    @Operation(summary = "设备事件管理日志分页查询")
    @GetMapping(value = "event/logs/page")
    public PageInfo<DeviceEventLogPO> eventLogsPage(DeviceEventLogQuery args) {
        DeviceEventLogCriteria criteria = deviceBeRestConvert.from(args);
        return deviceDataService.eventLogsPage(criteria);
    }

    @Operation(summary = "设备服务日志分页查询")
    @GetMapping(value = "service/logs/page")
    public PageInfo<DeviceServiceLogPO> page(DeviceServiceLogQuery args) {
        DeviceServiceLogCriteria criteria = deviceBeRestConvert.from(args);
        return deviceDataService.serviceLogsPage(criteria);
    }


}
