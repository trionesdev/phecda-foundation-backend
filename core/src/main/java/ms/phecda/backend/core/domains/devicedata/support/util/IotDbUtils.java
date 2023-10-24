package ms.phecda.backend.core.domains.devicedata.support.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ms.phecda.edge.base.commons.valuetype.ValueTypeEnum;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.pool.SessionDataSetWrapper;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.apache.iotdb.tsfile.read.common.Field;
import org.apache.iotdb.tsfile.read.common.RowRecord;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public static TSDataType typeConvert(ValueTypeEnum valueType) {

        switch (valueType) {
            case INT:
                return TSDataType.INT32;
            case FLOAT:
                return TSDataType.FLOAT;
            case DOUBLE:
                return TSDataType.DOUBLE;
            case BOOL:
                return TSDataType.BOOLEAN;
            default:
                return TSDataType.TEXT;
        }
    }

    public static Object valueConvert(ValueTypeEnum valueType, Object value) {
        if (Objects.isNull(value)) {
            return null;
        }
        switch (valueType) {
            case INT:
                return Integer.valueOf(value.toString());
            case FLOAT:
                return Float.valueOf(value.toString());
            case DOUBLE:
                return Double.valueOf(value.toString());
            case BOOL:
                return Boolean.valueOf(value.toString());
            default:
                return value.toString();
        }
    }

}
