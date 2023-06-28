package ms.triones.backend.core.modules.devicedata.service.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.devicedata.support.util.IotDbUtils;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.pool.SessionDataSetWrapper;
import org.apache.iotdb.session.pool.SessionPool;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.springframework.stereotype.Service;

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


    private String path(String nodeId, String deviceName) {
        return StrUtil.join(".", "root.phecda", nodeId, deviceName);
    }
}
