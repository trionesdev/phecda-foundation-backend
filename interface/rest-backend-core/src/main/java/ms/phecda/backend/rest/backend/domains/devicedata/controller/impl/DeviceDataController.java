package ms.phecda.backend.rest.backend.domains.devicedata.controller.impl;

import com.trionesdev.commons.core.page.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.devicedata.dao.criteria.DeviceDataCriteria;
import ms.phecda.backend.core.domains.devicedata.service.bo.DeviceDataBO;
import ms.phecda.backend.core.domains.devicedata.service.impl.DeviceDataService;
import ms.phecda.backend.rest.backend.domains.devicedata.controller.query.DeviceDataQuery;
import ms.phecda.backend.rest.backend.domains.devicedata.support.DeviceDataConstants;
import ms.phecda.backend.rest.backend.domains.devicedata.support.DeviceDataRestConvertMapper;
import org.springframework.validation.annotation.Validated;
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
    public List<DeviceDataBO> queryDeviceDataList(@Validated DeviceDataQuery query) {
        DeviceDataCriteria criteria = DeviceDataRestConvertMapper.INSTANCE.from(query);
        return deviceDataService.queryList(criteria);
    }

    @Operation(summary = "查询设备数据分页")
    @GetMapping(value = "device-datas/page")
    public PageInfo<DeviceDataBO> queryDeviceDataPage(
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            @Validated DeviceDataQuery query) {
        DeviceDataCriteria criteria = DeviceDataRestConvertMapper.INSTANCE.from(query);
        return deviceDataService.queryPage(pageNum, pageSize, criteria);
    }

    @Operation(summary = "查询设备某个属性最新的数据值")
    @GetMapping(value = "device-datas/latest")
    public DeviceDataBO latestData(@RequestParam String deviceName, @RequestParam String propertyIdentifier) {
        return deviceDataService.getLatestData(deviceName, propertyIdentifier);
    }


}
