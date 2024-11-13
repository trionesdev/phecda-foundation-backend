package com.trionesdev.phecda.backend.rest.backend.domains.device.controller.impl;

import cn.hutool.core.util.BooleanUtil;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.phecda.backend.core.domains.device.service.bo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.backend.core.domains.device.dao.criteria.DeviceCriteria;
import com.trionesdev.phecda.backend.core.domains.device.dao.dvo.DeviceStatisticsDVO;
import com.trionesdev.phecda.backend.core.domains.device.dao.po.DevicePO;
import com.trionesdev.phecda.backend.core.domains.device.service.impl.DeviceService;
import com.trionesdev.phecda.backend.core.messageaccess.model.ServiceInvokeReplyMessage;
import com.trionesdev.phecda.backend.rest.backend.domains.device.controller.ro.DeviceCreateRO;
import com.trionesdev.phecda.backend.rest.backend.domains.device.controller.ro.DeviceEnabledRO;
import com.trionesdev.phecda.backend.rest.backend.domains.device.controller.ro.DeviceProtocolUpdateRO;
import com.trionesdev.phecda.backend.rest.backend.domains.device.controller.query.DeviceQuery;
import com.trionesdev.phecda.backend.rest.backend.domains.device.controller.ro.DeviceUpdateRO;
import com.trionesdev.phecda.backend.rest.backend.domains.device.internal.DeviceBeRestConvert;
import com.trionesdev.phecda.backend.rest.backend.domains.device.internal.DeviceConstants;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "设备")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = DeviceConstants.DEVICE_URI)
public class DeviceController {
    private final DeviceBeRestConvert deviceBeRestConvert;
    private final DeviceService deviceService;

    @Operation(summary = "设备统计")
    @GetMapping(value = "devices/statistics")
    public DeviceStatisticsDVO statistics() {
        return deviceService.statistics();
    }

    @Operation(summary = "新建设备")
    @PostMapping(value = "devices")
    public void createDevice(@Validated @RequestBody DeviceCreateRO args) {
        DevicePO device = deviceBeRestConvert.from(args);
        deviceService.createDevice(device);
    }

    @Operation(summary = "更新设备")
    @PutMapping(value = "devices")
    public void updateDevice(@Validated @RequestBody DeviceUpdateRO args) {
        DevicePO device = deviceBeRestConvert.from(args);
        deviceService.updateById(device);
    }

    @Operation(summary = "根据ID删除设备")
    @DeleteMapping(value = "devices/{id}")
    public void deleteDeviceById(
            @PathVariable(value = "id") String id
    ) {
        deviceService.deleteDeviceById(id);
    }

    @Operation(summary = "根据ID获取设备信息(扩展)")
    @GetMapping(value = "devices/ext/{id}")
    public DeviceExtBO queryExtById(
            @PathVariable(value = "id") String id
    ) {
        return deviceService.queryExtById(id).orElse(null);
    }

    @Operation(summary = "查询设备列表(扩展分页)")
    @GetMapping(value = "devices/ext/page")
    public PageInfo<DeviceExtBO> queryExtPage(
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            DeviceQuery query
    ) {
        DeviceCriteria criteria = deviceBeRestConvert.from(query);
        return deviceService.queryExtPage(pageNum, pageSize, criteria);
    }

    @Operation(summary = "修改禁用/启用状态")
    @PutMapping(value = "devices/{id}/enabled")
    public void deviceEnable(
            @PathVariable(value = "id") String id,
            @RequestBody DeviceEnabledRO args
    ) {
        if (BooleanUtil.isTrue(args.getEnabled())) {
            deviceService.deviceOnline(id);
        } else {
            deviceService.deviceOffline(id);
        }
    }

    @Operation(summary = "获取设备属性数据")
    @GetMapping(value = "devices/{id}/properties-data")
    public List<DevicePropertyDataBO> queryDevicePropertiesData(
            @PathVariable(value = "id") String id
    ) {
        return deviceService.queryDeviceThingModelPropertiesData(id);
    }

    @Operation(summary = "获取设备事件数据")
    @GetMapping(value = "devices/{id}/events-data")
    public List<DeviceEventDataBO> queryDeviceEventsData(
            @PathVariable(value = "id") String id
    ) {
        return deviceService.queryDeviceThingModelEventsData(id);
    }

    @Operation(summary = "获取设备服务数据")
    @GetMapping(value = "devices/{id}/services-data")
    public List<DeviceServiceDataBO> queryDeviceServicesData(
            @PathVariable(value = "id") String id
    ) {
        return deviceService.queryDeviceThingModelServicesData(id);
    }

    @Operation(summary = "获取所有设备")
    @GetMapping(value = "devices/all")
    public List<DevicePO> queryAllDevice() {
        return deviceService.queryAllDevice();
    }

    @Operation(summary = "获取未被资产关联的设备")
    @GetMapping(value = "devices/{assetSn}/no-relation")
    public List<DevicePO> queryNoRelationDevice(
            @PathVariable(value = "assetSn", required = false) String assetSn
    ) {
        return deviceService.queryNoRelationDevice(assetSn);
    }

    @Operation(summary = "获取资产关联的设备")
    @GetMapping(value = "devices/{assetSn}/related-asset")
    public List<DevicePO> queryAssetRelationDevice(
            @PathVariable(value = "assetSn", required = false) String assetSn
    ) {
        return deviceService.queryAssetRelationDevice(assetSn);
    }

    @Operation(summary = "查询设备列表(不分页)")
    @GetMapping(value = "devices/list")
    public List<DevicePO> queryDeviceList(
            DeviceQuery query
    ) {
        DeviceCriteria criteria = deviceBeRestConvert.from(query);
        return deviceService.queryList(criteria);
    }

    @Operation(summary = "根据设备name获取设备物模型属性")
    @GetMapping(value = "devices/{deviceName}/properties")
    public List<DevicePropertyDataBO> queryDeviceProperties(
            @PathVariable(value = "deviceName", required = false) String deviceName
    ) {
        return deviceService.queryDeviceProperties(deviceName);
    }

    @Operation(summary = "添加网关子设备")
    @PostMapping(value = "devices/{id}/children/{ids}")
    public void addChildDevice(
            @PathVariable(value = "id") String parentDeviceId,
            @PathVariable(value = "ids") List<String> childDeviceIds) {
        deviceService.addChildDevice(parentDeviceId, childDeviceIds);
    }

    @Operation(summary = "删除网关子设备（只是移除与父设备之间的关系）")
    @DeleteMapping(value = "devices/{id}/children/{ids}")
    public void removeChildDevice(
            @PathVariable(value = "id") String parentDeviceId,
            @PathVariable(value = "ids") List<String> childDeviceIds) {
        deviceService.removeChildDevice(parentDeviceId, childDeviceIds);
    }

    @Operation(summary = "根据ID修改协议值")
    @PutMapping(value = "devices/{deviceId}/protocol")
    public void updateDeviceProtocol(
            @PathVariable(value = "deviceId") String productId,
            @RequestBody DeviceProtocolUpdateRO args) {
        deviceService.updateById(DevicePO.builder()
                .id(productId)
                .protocols(args.getProtocols())
                .build());
    }

    @Operation(summary = "开始推流")
    @GetMapping(value = "devices/{id}/streaming/start")
    public String startDeviceStream(
            @PathVariable(value = "id") String id
    ) {
        return deviceService.startPushStreaming(id);
    }

    @Operation(summary = "结束推流")
    @GetMapping(value = "devices/{id}/streaming/stop")
    public void stopDeviceStream(
            @PathVariable(value = "id") String id
    ) {
        deviceService.stopPushStreaming(id);
    }

    @Operation(summary = "服务同步调用")
    @PostMapping(value = "devices/{id}/service/sync/invoke")
    public ServiceInvokeReplyMessage serviceInvoke(@PathVariable(value = "id") String id, @RequestBody InvokeServiceArgBO bo) {
        return deviceService.invokeService(id, bo);
    }
}
