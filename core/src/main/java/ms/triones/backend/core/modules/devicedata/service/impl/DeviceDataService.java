package ms.triones.backend.core.modules.devicedata.service.impl;

import cn.hutool.core.util.StrUtil;
import com.moensun.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.devicedata.service.bo.DeviceDataBO;
import ms.triones.backend.core.modules.devicedata.service.bo.DeviceDataQueryBO;
import ms.triones.backend.core.modules.devicedata.support.util.IotDbUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.pool.SessionDataSetWrapper;
import org.apache.iotdb.session.pool.SessionPool;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DeviceDataService {

    private final SessionPool sessionPool;

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
        try (SessionDataSetWrapper sessionDataSet = sessionPool.executeRawDataQuery(paths, startTime, endTime, 6000)) {
            return IotDbUtils.toList(sessionDataSet);
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> queryLastData(String nodeId, String deviceName, List<String> fields) {
        String devicePath = path(nodeId, deviceName);
        List<String> paths = fields.stream().map(field -> devicePath + "." + field).collect(Collectors.toList());
        try (SessionDataSetWrapper sessionDataSet = sessionPool.executeLastDataQuery(paths)) {
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


    public List<DeviceDataBO> queryList(DeviceDataQueryBO queryBO) {
        List<Map<String, Object>> rawDataList = queryRawData("default", queryBO.getDeviceName(), Lists.newArrayList(queryBO.getField()),
                queryBO.getStartTime().toEpochMilli(), queryBO.getEndTime().toEpochMilli());
        return convertToBO(rawDataList, queryBO);
    }

    public PageInfo<DeviceDataBO> queryPage(Integer pageNum, Integer pageSize, DeviceDataQueryBO queryBO) {
        List<DeviceDataBO> deviceDataBOS = queryList(queryBO);
        return PageInfo.<DeviceDataBO>builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .rows(deviceDataBOS)
                .build();
    }

    private List<DeviceDataBO> convertToBO(List<Map<String, Object>> rawDataList, DeviceDataQueryBO queryBO) {
        List<DeviceDataBO> deviceDataBOS = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(rawDataList)) {
            for (Map<String, Object> rwaData : rawDataList) {
                Instant time = Instant.ofEpochMilli(Long.parseLong(String.valueOf(rwaData.get("Time"))));
                String devicePath = path("default", queryBO.getDeviceName() + "." + queryBO.getField());

                DeviceDataBO deviceDataBO = DeviceDataBO.builder()
                        .time(time)
                        .value(rwaData.get(devicePath))
                        .field(queryBO.getField())
                        .assetSn(queryBO.getAssetSn())
                        .deviceName(queryBO.getDeviceName())
                        .build();
                deviceDataBOS.add(deviceDataBO);
            }
        }

        return deviceDataBOS;
    }
}
