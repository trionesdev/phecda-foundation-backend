package ms.triones.backend.rest.backend.modules.device.controller.impl;

import com.moensun.commons.core.page.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.device.dao.criteria.DeviceCriteria;
import ms.triones.backend.core.modules.device.dao.entity.Device;
import ms.triones.backend.core.modules.device.service.bo.DeviceExtBO;
import ms.triones.backend.core.modules.device.service.impl.DeviceService;
import ms.triones.backend.rest.backend.modules.device.controller.query.DeviceQuery;
import ms.triones.backend.rest.backend.modules.device.controller.ro.DeviceCreateRO;
import ms.triones.backend.rest.backend.modules.device.support.DeviceConstants;
import ms.triones.backend.rest.backend.modules.device.support.DeviceRestConvertMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "设备")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = DeviceConstants.DEVICE_URI)
public class DeviceController {
    private final DeviceService deviceService;

    @Operation(summary = "新建设备")
    @PostMapping(value = "devices")
    public void createDevice( @RequestBody DeviceCreateRO args) {
        Device device = DeviceRestConvertMapper.INSTANT.from(args);
        deviceService.createDevice(device);
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

}
