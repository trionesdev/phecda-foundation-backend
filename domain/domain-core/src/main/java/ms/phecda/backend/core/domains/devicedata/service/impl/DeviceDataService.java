package ms.phecda.backend.core.domains.devicedata.service.impl;

import cn.hutool.core.util.StrUtil;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.domains.device.dao.criteria.DevicePropertyCriteria;
import ms.phecda.backend.core.provider.ssp.device.impl.DeviceProvider;
import ms.phecda.backend.core.provider.ssp.device.pdo.DevicePDO;
import ms.phecda.backend.core.domains.devicedata.service.bo.DeviceDataBO;
import ms.phecda.backend.core.domains.device.internal.util.IotDbUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.pool.SessionDataSetWrapper;
import org.apache.iotdb.session.pool.SessionPool;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.apache.iotdb.tsfile.read.common.Field;
import org.apache.iotdb.tsfile.read.common.RowRecord;
import org.assertj.core.util.Lists;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Deprecated
@Slf4j
@RequiredArgsConstructor
//@Service
public class DeviceDataService {
    private final SessionPool sessionPool;
    private final DeviceProvider deviceProvider;

    public void insertRecord(String productId, String deviceName, long time, List<String> measurements, List<TSDataType> types, List<Object> values) {
        try {
            sessionPool.insertRecord(path(productId, deviceName), time, measurements, types, values);
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertRecord(String productId, String deviceName, long time, List<String> measurements, List<String> values) {
        try {
            sessionPool.insertRecord(path(productId, deviceName), time, measurements, values);
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> queryRawData(String deviceName,List<String> fields, long startTime, long endTime) {
        DevicePDO devicePDO = deviceProvider.queryByName(deviceName);
        if (Objects.isNull(devicePDO)) {
            throw new NotFoundException("not found device");
        }

        String devicePath = path(devicePDO.getProductId(), deviceName);
        List<String> paths = fields.stream().map(field -> devicePath + "." + field).collect(Collectors.toList());
        try {
            SessionDataSetWrapper sessionDataSet = sessionPool.executeRawDataQuery(paths, startTime, endTime, 6000);
            return IotDbUtils.toList(sessionDataSet);
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> queryLastData(String deviceName, List<String> fields) {
        DevicePDO devicePDO = deviceProvider.queryByName(deviceName);
        if (Objects.isNull(devicePDO)) {
            throw new NotFoundException("not found device");
        }

        String devicePath = path(devicePDO.getProductId(), deviceName);
        List<String> paths = fields.stream().map(field -> devicePath + "." + field).collect(Collectors.toList());
        try {
            SessionDataSetWrapper sessionDataSet = sessionPool.executeLastDataQuery(paths);
            return IotDbUtils.toList(sessionDataSet);
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> queryRawDataWithPagination(String productId, String deviceName, List<String> fields,
                                                                long startTime, long endTime, int rowLimit, int rowOffset) {
        String selectExpr = StrUtil.join(",", fields);
        String whereCondition = "time >=" + startTime + " and time <= " + endTime;
        String sql = "select " + selectExpr +
                " from " + path(productId, deviceName) +
                " where " + whereCondition +
                " limit " + rowLimit +
                " offset " + rowOffset;
        try (SessionDataSetWrapper sessionDataSet = sessionPool.executeQueryStatement(sql, 6000)) {
            return IotDbUtils.toList(sessionDataSet);
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    private String path(String productId, String deviceName) {
        return StrUtil.join(".", "root.phecda", "p" + productId, deviceName);
    }


    public List<DeviceDataBO> queryList(DevicePropertyCriteria criteria) {
        List<Map<String, Object>> rawDataList = queryRawData(criteria.getDeviceName(), Lists.newArrayList(criteria.getField()),
                criteria.getStartTime().toEpochMilli(), criteria.getEndTime().toEpochMilli());
        return convertToBO(rawDataList, criteria);
    }

    public PageInfo<DeviceDataBO> queryPage(Integer pageNum, Integer pageSize, DevicePropertyCriteria criteria) {
        List<DeviceDataBO> deviceDataBOS = queryList(criteria);
        return PageInfo.<DeviceDataBO>builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .rows(deviceDataBOS)
                .build();
    }

    private List<DeviceDataBO> convertToBO(List<Map<String, Object>> rawDataList, DevicePropertyCriteria criteria) {
        List<DeviceDataBO> deviceDataBOS = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(rawDataList)) {
            DevicePDO devicePDO = deviceProvider.queryByName(criteria.getDeviceName());
            if (Objects.isNull(devicePDO)) {
                throw new NotFoundException("not found device");
            }

            for (Map<String, Object> rwaData : rawDataList) {
                String timeStr = String.valueOf(rwaData.get("Time")).substring(0, 13);
                Instant time = Instant.ofEpochMilli(Long.parseLong(timeStr));
                String devicePath = path(devicePDO.getProductId(), criteria.getDeviceName() + "." + criteria.getField());

                DeviceDataBO deviceDataBO = DeviceDataBO.builder()
                        .time(time)
                        .value(rwaData.get(devicePath))
                        .field(criteria.getField())
                        .deviceName(criteria.getDeviceName())
                        .build();
                deviceDataBOS.add(deviceDataBO);
            }
        }

        return deviceDataBOS;
    }

    public DeviceDataBO getLatestData(String deviceName, String propertyIdentifier) {
        try {
            DevicePDO devicePDO = deviceProvider.queryByName(deviceName);
            if (Objects.isNull(devicePDO)) {
                throw new NotFoundException("not found device");
            }

            SessionDataSetWrapper sessionDataSet = sessionPool.executeQueryStatement("select last " + propertyIdentifier
                    + " from " + path(devicePDO.getProductId(), deviceName));

            DeviceDataBO deviceDataBO = DeviceDataBO.builder().build();
            while (sessionDataSet.hasNext()) {
                RowRecord rowRecord = sessionDataSet.next();
                if (rowRecord.isAllNull()) {
                    break;
                }

                deviceDataBO.setTime(Instant.ofEpochMilli(rowRecord.getTimestamp()));

                Field field = rowRecord.getFields().get(1);
                deviceDataBO.setValue(IotDbUtils.fieldValue(field));
            }
            return deviceDataBO;
        } catch (Exception e) {
            log.error("getLatestData fail: ", e);
        }

        return null;
    }
}
