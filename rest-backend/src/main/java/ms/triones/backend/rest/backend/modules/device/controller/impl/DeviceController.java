package ms.triones.backend.rest.backend.modules.device.controller.impl;

import cn.hutool.core.util.BooleanUtil;
import com.moensun.commons.core.page.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.device.dao.criteria.DeviceCriteria;
import ms.triones.backend.core.modules.device.dao.entity.Device;
import ms.triones.backend.core.modules.device.service.bo.DeviceEventDataBO;
import ms.triones.backend.core.modules.device.service.bo.DeviceExtBO;
import ms.triones.backend.core.modules.device.service.bo.DevicePropertyDataBO;
import ms.triones.backend.core.modules.device.service.bo.DeviceServiceDataBO;
import ms.triones.backend.core.modules.device.service.impl.DeviceService;
import ms.triones.backend.rest.backend.modules.device.controller.query.DeviceQuery;
import ms.triones.backend.rest.backend.modules.device.controller.ro.DeviceCreateRO;
import ms.triones.backend.rest.backend.modules.device.controller.ro.DeviceEnabledRO;
import ms.triones.backend.rest.backend.modules.device.support.DeviceConstants;
import ms.triones.backend.rest.backend.modules.device.support.DeviceRestConvertMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "设备")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = DeviceConstants.DEVICE_URI)
public class DeviceController {
    private final DeviceService deviceService;

    @Operation(summary = "新建设备")
    @PostMapping(value = "devices")
    public void createDevice(@Validated @RequestBody DeviceCreateRO args) {
        Device device = DeviceRestConvertMapper.INSTANT.from(args);
        deviceService.createDevice(device);
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
        DeviceCriteria criteria = DeviceRestConvertMapper.INSTANT.from(query);
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
    public List<Device> queryAllDevice() {
        return deviceService.queryAllDevice();
    }

    @Operation(summary = "获取未被资产关联的设备")
    @GetMapping(value = "devices/{assetSn}/no-relation")
    public List<Device> queryNoRelationDevice(
            @PathVariable(value = "assetSn", required = false) String assetSn
    ) {
        return deviceService.queryNoRelationDevice(assetSn);
    }

    @Operation(summary = "获取资产关联的设备")
    @GetMapping(value = "devices/{assetSn}/related-asset")
    public List<Device> queryAssetRelationDevice(
            @PathVariable(value = "assetSn", required = false) String assetSn
    ) {
        return deviceService.queryAssetRelationDevice(assetSn);
    }

    @Operation(summary = "查询设备列表(不分页)")
    @GetMapping(value = "devices/list")
    public List<Device> queryDeviceList(
            DeviceQuery query
    ) {
        DeviceCriteria criteria = DeviceRestConvertMapper.INSTANT.from(query);
        return deviceService.queryList(criteria);
    }

    @Operation(summary = "根据设备name获取设备物模型属性")
    @GetMapping(value = "devices/{deviceName}/properties")
    public List<DevicePropertyDataBO> queryDeviceProperties(
            @PathVariable(value = "deviceName", required = false) String deviceName
    ) {
        return deviceService.queryDeviceProperties(deviceName);
    }

    @Operation(summary = "添加子设备")
    @PostMapping(value = "devices/{id}/children/{ids}")
    public void addChildDevice(
            @PathVariable(value = "id") String parentDeviceId,
            @PathVariable(value = "ids") List<String> childDeviceIds) {
        deviceService.addChildDevice(parentDeviceId, childDeviceIds);
    }

    @Operation(summary = "删除子设备（只是移除与父设备之间的关系）")
    @DeleteMapping(value = "devices/{id}/children/{ids}")
    public void removeChildDevice(
            @PathVariable(value = "id") String parentDeviceId,
            @PathVariable(value = "ids") List<String> childDeviceIds) {
        deviceService.removeChildDevice(parentDeviceId, childDeviceIds);
    }
}
