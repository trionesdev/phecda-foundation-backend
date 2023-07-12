package ms.triones.backend.rest.backend.modules.devicedata.controller.impl;

import com.moensun.commons.core.page.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.devicedata.service.bo.DeviceDataBO;
import ms.triones.backend.core.modules.devicedata.service.bo.DeviceDataQueryBO;
import ms.triones.backend.core.modules.devicedata.service.impl.DeviceDataService;
import ms.triones.backend.rest.backend.modules.devicedata.controller.query.DeviceDataQuery;
import ms.triones.backend.rest.backend.modules.devicedata.support.DeviceDataConstants;
import ms.triones.backend.rest.backend.modules.devicedata.support.DeviceDataRestConvertMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "设备数据")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = DeviceDataConstants.DEVICE_DATA_URI)
public class DeviceDataController {

    private final DeviceDataService deviceDataService;
    @Operation(summary = "查询设备数据列表")
    @GetMapping(value = "device-datas/list")
    public List<DeviceDataBO> queryDeviceDataList(DeviceDataQuery query) {
        DeviceDataQueryBO queryBO = DeviceDataRestConvertMapper.INSTANCE.from(query);
        return deviceDataService.queryList(queryBO);
    }

    @Operation(summary = "查询设备数据分页")
    @GetMapping(value = "device-datas/page")
    public PageInfo<DeviceDataBO> queryDeviceDataPage(
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            DeviceDataQuery query) {
        DeviceDataQueryBO queryBO = DeviceDataRestConvertMapper.INSTANCE.from(query);
        return deviceDataService.queryPage(pageNum, pageSize, queryBO);
    }


}
