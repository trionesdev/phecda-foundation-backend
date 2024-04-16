package ms.phecda.backend.core.domains.device.service.impl;

import com.trionesdev.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.dao.criteria.DeviceEventLogCriteria;
import ms.phecda.backend.core.domains.device.dao.criteria.DevicePropertyDataCriteria;
import ms.phecda.backend.core.domains.device.dao.criteria.DeviceServiceLogCriteria;
import ms.phecda.backend.core.domains.device.dao.entity.DeviceEventLog;
import ms.phecda.backend.core.domains.device.dao.entity.DeviceServiceLog;
import ms.phecda.backend.core.domains.device.manager.dto.DevicePropertyDataDTO;
import ms.phecda.backend.core.domains.device.manager.impl.DeviceDataManager;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DeviceDataService {
    private final DeviceDataManager deviceDataManager;

    //region event
    public PageInfo<DeviceEventLog> eventLogsPage(DeviceEventLogCriteria criteria) {
        return deviceDataManager.eventLogsPage(criteria);
    }
    //endregion


    //region service
    public PageInfo<DeviceServiceLog> serviceLogsPage(DeviceServiceLogCriteria criteria) {
        return deviceDataManager.serviceLogsPage(criteria);
    }

    public void saveServiceLog(DeviceServiceLog entity) {
        deviceDataManager.saveServiceLog(entity);
    }
    //endregion


    //region property
    public void savePropertyData(String productKey, String deviceName, long time, List<String> measurements, List<TSDataType> types, List<Object> values) {
        deviceDataManager.savePropertyData(productKey, deviceName, time, measurements, types, values);
    }

    public List<DevicePropertyDataDTO> queryDevicePropertyDataList(DevicePropertyDataCriteria criteria) {
        return deviceDataManager.queryDevicePropertyDataList(criteria);
    }
    //endregion
}
