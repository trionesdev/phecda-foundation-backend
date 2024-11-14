package com.trionesdev.phecda.foundation.rest.tenant.domains.devicedata.controller.impl;

import com.trionesdev.commons.core.page.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.DevicePropertyDataCriteria;
import com.trionesdev.phecda.foundation.core.domains.devicedata.service.bo.DeviceDataBO;
import com.trionesdev.phecda.foundation.core.domains.devicedata.service.impl.DeviceDataService;
import com.trionesdev.phecda.foundation.rest.tenant.domains.devicedata.controller.query.DeviceDataQuery;
import com.trionesdev.phecda.foundation.rest.tenant.domains.devicedata.support.DeviceDataConstants;
import com.trionesdev.phecda.foundation.rest.tenant.domains.devicedata.support.DeviceDataRestConvertMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Deprecated
@Tag(name = "设备数据")
@RequiredArgsConstructor
//@RestController
@RequestMapping(value = DeviceDataConstants.DEVICE_DATA_URI)
public class DeviceDataController {
    private final DeviceDataService deviceDataService;

    @Operation(summary = "查询设备数据列表")
    @GetMapping(value = "device-datas/list")
    public List<DeviceDataBO> queryDeviceDataList(@Validated DeviceDataQuery query) {
        DevicePropertyDataCriteria criteria = DeviceDataRestConvertMapper.INSTANCE.from(query);
        return deviceDataService.queryList(criteria);
    }

    @Operation(summary = "查询设备数据分页")
    @GetMapping(value = "device-datas/page")
    public PageInfo<DeviceDataBO> queryDeviceDataPage(
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            @Validated DeviceDataQuery query) {
        DevicePropertyDataCriteria criteria = DeviceDataRestConvertMapper.INSTANCE.from(query);
        return deviceDataService.queryPage(pageNum, pageSize, criteria);
    }

    @Operation(summary = "查询设备某个属性最新的数据值")
    @GetMapping(value = "device-datas/latest")
    public DeviceDataBO latestData(@RequestParam String deviceName, @RequestParam String propertyIdentifier) {
        return deviceDataService.getLatestData(deviceName, propertyIdentifier);
    }


}
