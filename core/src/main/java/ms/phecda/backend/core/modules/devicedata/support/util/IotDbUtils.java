package ms.phecda.backend.core.modules.devicedata.support.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.pool.SessionDataSetWrapper;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.apache.iotdb.tsfile.read.common.Field;
import org.apache.iotdb.tsfile.read.common.RowRecord;

import java.util.List;
import java.util.Map;

public class IotDbUtils {

    public static List<Map<String, Object>> toList(SessionDataSetWrapper wrapper) throws IoTDBConnectionException, StatementExecutionException {
        List<Map<String, Object>> results = Lists.newArrayList();
        List<String> columnNames = wrapper.getColumnNames();
        while (wrapper.hasNext()) {
            RowRecord rowRecord = wrapper.next();
            Map<String, Object> row = Maps.newHashMap();
            row.put(columnNames.get(0), String.valueOf(rowRecord.getTimestamp()));
            if (!rowRecord.getFields().isEmpty()) {
                for (int i = 0; i < rowRecord.getFields().size(); i++) {
                    row.put(columnNames.get(i + 1), fieldValue(rowRecord.getFields().get(i)));
                }
            }
            results.add(row);
        }
        return results;
    }

    public static Object fieldValue(Field field) {
        switch (field.getDataType()) {
            case BOOLEAN:
                return field.getBoolV();
            case INT32:
                return field.getIntV();
            case INT64:
                return field.getLongV();
            case FLOAT:
                return field.getFloatV();
            case DOUBLE:
                return field.getDoubleV();
            case VECTOR:
                return field.getObjectValue(TSDataType.VECTOR);
            case TEXT:
                return field.getStringValue();
            default:
                return field.getBinaryV();
        }
    }

}
