package ms.phecda.backend.core.domains.device.internal.util;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ms.phecda.backend.core.domains.device.internal.model.thing.valuetype.ValueTypeEnum;
import org.apache.iotdb.isession.pool.SessionDataSetWrapper;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.apache.iotdb.tsfile.read.common.Field;
import org.apache.iotdb.tsfile.read.common.RowRecord;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class IotDbUtils {


    public static String generatePath(List<String> parts) {
        return StrUtil.join(".", "root.phecda", parts);
    }

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
        return switch (field.getDataType()) {
            case BOOLEAN -> field.getBoolV();
            case INT32 -> field.getIntV();
            case INT64 -> field.getLongV();
            case FLOAT -> field.getFloatV();
            case DOUBLE -> field.getDoubleV();
            case VECTOR -> field.getObjectValue(TSDataType.VECTOR);
            case TEXT -> field.getStringValue();
            default -> field.getBinaryV();
        };
    }

    public static TSDataType typeConvert(ValueTypeEnum valueType) {

        return switch (valueType) {
            case INT -> TSDataType.INT32;
            case FLOAT -> TSDataType.FLOAT;
            case DOUBLE -> TSDataType.DOUBLE;
            case BOOL -> TSDataType.BOOLEAN;
            default -> TSDataType.TEXT;
        };
    }

    public static Object valueConvert(ValueTypeEnum valueType, Object value) {
        if (Objects.isNull(value)) {
            return null;
        }
        return switch (valueType) {
            case INT -> Integer.valueOf(value.toString());
            case FLOAT -> Float.valueOf(value.toString());
            case DOUBLE -> Double.valueOf(value.toString());
            case BOOL -> Boolean.valueOf(value.toString());
            default -> value.toString();
        };
    }

}
