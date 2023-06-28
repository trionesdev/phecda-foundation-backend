package ms.triones.backend.core.modules.devicedata.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.SessionDataSet;
import org.apache.iotdb.session.pool.SessionDataSetWrapper;
import org.apache.iotdb.session.pool.SessionPool;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.apache.iotdb.tsfile.read.common.Field;
import org.apache.iotdb.tsfile.read.common.RowRecord;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class DeviceDataService {

    private final SessionPool sessionPool;

    public void insertRecord(String nodeId, String deviceName, long time, List<String> measurements, List<TSDataType> types, List<Object> values) {
        try {
            sessionPool.insertRecord("root.phecda.default.deveice1", time, measurements, types, values);
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertRecord(String nodeId, String deviceName, long time, List<String> measurements, List<String> values) {
        try {
            sessionPool.insertRecord("root.phecda.default.deveice1", time, measurements, values);
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, String>> executeLastDataQuery(String nodeId, String deviceName) {
        try (SessionDataSetWrapper sessionDataSet = sessionPool.executeQueryStatement("select last * from root.phecda.default.deveice1")) {
            List<Map<String, String>> results = Lists.newArrayList();
            List<String> columnNames = sessionDataSet.getColumnNames();
            while (sessionDataSet.hasNext()) {
                RowRecord rowRecord = sessionDataSet.next();
                Map<String, String> row = Maps.newHashMap();
                row.put(columnNames.get(0), String.valueOf(rowRecord.getTimestamp()));
                if (!rowRecord.getFields().isEmpty()) {
                    for (int i = 0; i < rowRecord.getFields().size(); i++) {
                        row.put(columnNames.get(i + 1), rowRecord.getFields().get(i).getStringValue());
                    }
                }
                results.add(row);
            }
            return results;
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }

    }

}
