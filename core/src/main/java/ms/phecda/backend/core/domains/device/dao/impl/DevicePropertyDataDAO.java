package ms.phecda.backend.core.domains.device.dao.impl;

import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.internal.util.IotDbUtils;
import org.apache.iotdb.isession.pool.SessionDataSetWrapper;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.pool.SessionPool;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class DevicePropertyDataDAO {
    private final SessionPool sessionPool;

    public void insertRecord(String path, long time, List<String> measurements, List<TSDataType> types, List<Object> values) {
        try {
            sessionPool.insertRecord(path, time, measurements, types, values);
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertRecord(String path, long time, List<String> measurements, List<String> values) {
        try {
            sessionPool.insertRecord(path, time, measurements, values);
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> selectList(List<String> paths, long startTime, long endTime) {
        try {
            SessionDataSetWrapper sessionDataSet = sessionPool.executeRawDataQuery(paths, startTime, endTime, 6000);
            return IotDbUtils.toList(sessionDataSet);
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> selectLastList(List<String> paths) {
        try {
            SessionDataSetWrapper sessionDataSet = sessionPool.executeLastDataQuery(paths);
            return IotDbUtils.toList(sessionDataSet);
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
    }


}
