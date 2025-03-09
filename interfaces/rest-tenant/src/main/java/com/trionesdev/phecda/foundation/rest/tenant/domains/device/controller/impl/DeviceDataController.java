package com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.impl;

import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.phecda.foundation.core.domains.device.dto.DevicePropertyDataDTO;
import com.trionesdev.phecda.foundation.core.domains.device.dto.PropertyDataDTO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro.DevicePropertyDataLastQueryRO;
import com.trionesdev.phecda.infrastructure.tsdb.schema.TsDbCell;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.DeviceEventLogCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.DevicePropertyDataCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.DeviceServiceLogCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.DeviceEventLogPO;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.DeviceCommandLogPO;
import com.trionesdev.phecda.foundation.core.domains.device.service.bo.DevicePropertiesPostStatisticsBO;
import com.trionesdev.phecda.foundation.core.domains.device.service.impl.DeviceDataService;
import com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro.DeviceEventLogQueryRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro.DevicePropertyDataQueryRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro.DeviceCommandLogQueryRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.device.internal.DeviceBeRestConvert;
import com.trionesdev.phecda.foundation.rest.tenant.domains.device.internal.DeviceConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    @Operation(summary = "查询设备最新属性数据")
    @GetMapping(value = "property/data/last")
    public Map<String, Object> queryPropertyDataLast(DevicePropertyDataLastQueryRO query) {
        return deviceDataService.queryPropertyLast(query.getDeviceName(), Collections.singletonList(query.getIdentifier()));
    }

    @Operation(summary = "查询设备属性数据列表")
    @GetMapping(value = "property/data/list")
    public List<List<TsDbCell>> queryProperties(DevicePropertyDataQueryRO query) {
        DevicePropertyDataCriteria criteria = deviceBeRestConvert.from(query);
        return deviceDataService.queryDevicePropertyDataList(criteria);
    }

    @Operation(summary = "设备事件日志(分页)")
    @GetMapping(value = "event/log/page")
    public PageInfo<DeviceEventLogPO> eventLogsPage(DeviceEventLogQueryRO args) {
        DeviceEventLogCriteria criteria = deviceBeRestConvert.from(args);
        return deviceDataService.eventLogsPage(criteria);
    }

    @Operation(summary = "设备指令日志(分页)")
    @GetMapping(value = "command/log/page")
    public PageInfo<DeviceCommandLogPO> page(DeviceCommandLogQueryRO args) {
        DeviceServiceLogCriteria criteria = deviceBeRestConvert.from(args);
        return deviceDataService.serviceLogsPage(criteria);
    }


}
