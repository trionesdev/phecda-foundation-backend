package ms.triones.backend.core.modules.devicedata.service.impl;

import cn.hutool.core.util.StrUtil;
import com.moensun.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.triones.backend.core.modules.devicedata.dao.criteria.DeviceDataCriteria;
import ms.triones.backend.core.modules.devicedata.service.bo.DeviceDataBO;
import ms.triones.backend.core.modules.devicedata.support.util.IotDbUtils;
import ms.triones.backend.core.provider.ssp.device.impl.DeviceProvider;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.pool.SessionDataSetWrapper;
import org.apache.iotdb.session.pool.SessionPool;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.apache.iotdb.tsfile.read.common.Field;
import org.apache.iotdb.tsfile.read.common.RowRecord;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeviceDataService {

    private final SessionPool sessionPool;
    private final DeviceProvider deviceProvider;

    public void insertRecord(String nodeId, String deviceName, long time, List<String> measurements, List<TSDataType> types, List<Object> values) {
        try {
            sessionPool.insertRecord(path(nodeId, deviceName), time, measurements, types, values);
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertRecord(String nodeId, String deviceName, long time, List<String> measurements, List<String> values) {
        try {
            sessionPool.insertRecord(path(nodeId, deviceName), time, measurements, values);
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> queryRawData(String nodeId, String deviceName,List<String> fields, long startTime, long endTime) {
        String devicePath = path(nodeId, deviceName);
        List<String> paths = fields.stream().map(field -> devicePath + "." + field).collect(Collectors.toList());
        try {
            SessionDataSetWrapper sessionDataSet = sessionPool.executeRawDataQuery(paths, startTime, endTime, 6000);
            return IotDbUtils.toList(sessionDataSet);
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> queryLastData(String nodeId, String deviceName, List<String> fields) {
        String devicePath = path(nodeId, deviceName);
        List<String> paths = fields.stream().map(field -> devicePath + "." + field).collect(Collectors.toList());
        try {
            SessionDataSetWrapper sessionDataSet = sessionPool.executeLastDataQuery(paths);
            return IotDbUtils.toList(sessionDataSet);
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> queryLastData(String nodeId, String deviceName) {
        try (SessionDataSetWrapper sessionDataSet = sessionPool.executeQueryStatement("select last * from " + path(nodeId, deviceName))) {
            return IotDbUtils.toList(sessionDataSet);
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> queryRawDataWithPagination(String nodeId, String deviceName, List<String> fields,
                                                                long startTime, long endTime, int rowLimit, int rowOffset) {
        String selectExpr = StrUtil.join(",", fields);
        String whereCondition = "time >=" + startTime + " and time <= " + endTime;
        String sql = "select " + selectExpr +
                " from " + path(nodeId, deviceName) +
                " where " + whereCondition +
                " limit " + rowLimit +
                " offset " + rowOffset;
        try (SessionDataSetWrapper sessionDataSet = sessionPool.executeQueryStatement(sql, 6000)) {
            return IotDbUtils.toList(sessionDataSet);
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    private String path(String nodeId, String deviceName) {
        return StrUtil.join(".", "root.phecda", nodeId, deviceName);
    }


    public List<DeviceDataBO> queryList(DeviceDataCriteria criteria) {
        String nodeId = deviceProvider.getNodeIdByName(criteria.getDeviceName());
        criteria.setNodeId(nodeId);
        List<Map<String, Object>> rawDataList = queryRawData(criteria.getNodeId(), criteria.getDeviceName(), Lists.newArrayList(criteria.getField()),
                criteria.getStartTime().toEpochMilli() * 1000, criteria.getEndTime().toEpochMilli() * 1000);
        return convertToBO(rawDataList, criteria);
    }

    public PageInfo<DeviceDataBO> queryPage(Integer pageNum, Integer pageSize, DeviceDataCriteria criteria) {
        List<DeviceDataBO> deviceDataBOS = queryList(criteria);
        return PageInfo.<DeviceDataBO>builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .rows(deviceDataBOS)
                .build();
    }

    private List<DeviceDataBO> convertToBO(List<Map<String, Object>> rawDataList, DeviceDataCriteria criteria) {
        List<DeviceDataBO> deviceDataBOS = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(rawDataList)) {
            for (Map<String, Object> rwaData : rawDataList) {
                String timeStr = String.valueOf(rwaData.get("Time")).substring(0, 13);
                Instant time = Instant.ofEpochMilli(Long.parseLong(timeStr));
                String devicePath = path(criteria.getNodeId(), criteria.getDeviceName() + "." + criteria.getField());

                DeviceDataBO deviceDataBO = DeviceDataBO.builder()
                        .time(time)
                        .value(rwaData.get(devicePath))
                        .field(criteria.getField())
                        .assetSn(criteria.getAssetSn())
                        .deviceName(criteria.getDeviceName())
                        .build();
                deviceDataBOS.add(deviceDataBO);
            }
        }

        return deviceDataBOS;
    }

    public DeviceDataBO getLatestData(String deviceName, String propertyIdentifier) {
        try {
            String nodeId = deviceProvider.getNodeIdByName(deviceName);
            log.info("==query param, nodeId = {}, deviceName = {}, propertyIdentifier = {}", nodeId, deviceName, propertyIdentifier);
            SessionDataSetWrapper sessionDataSet = sessionPool.executeQueryStatement("select last " + propertyIdentifier + " from " + path(nodeId, deviceName));
            log.info("==query data: {}", sessionDataSet);

            DeviceDataBO deviceDataBO = DeviceDataBO.builder().build();
            while (sessionDataSet.hasNext()) {
                RowRecord rowRecord = sessionDataSet.next();
                if (rowRecord.isAllNull()) {
                    break;
                }

                deviceDataBO.setTime(Instant.ofEpochMilli(rowRecord.getTimestamp() / 1000));

                Field field = rowRecord.getFields().get(1);
                deviceDataBO.setValue(field.getObjectValue(field.getDataType()));
            }
            return deviceDataBO;
        } catch (Exception e) {
            log.error("getLatestData fail: ", e);
        }

        return null;
    }
}
