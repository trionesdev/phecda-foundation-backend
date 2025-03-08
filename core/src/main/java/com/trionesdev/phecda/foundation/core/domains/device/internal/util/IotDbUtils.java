package com.trionesdev.phecda.foundation.core.domains.device.internal.util;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.trionesdev.phecda.foundation.core.domains.device.shared.model.IotDbCell;
import com.trionesdev.phecda.foundation.core.domains.device.shared.model.IotDbColumn;
import com.trionesdev.phecda.foundation.core.domains.device.shared.model.IotDbSave;
import com.trionesdev.phecda.model.device.PhecdaMessage;
import com.trionesdev.phecda.model.device.thing.valuetype.ValueTypeEnum;
import org.apache.commons.collections4.MapUtils;
import org.apache.iotdb.isession.SessionDataSet;
import org.apache.iotdb.isession.pool.SessionDataSetWrapper;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.tsfile.enums.TSDataType;
import org.apache.tsfile.read.common.Field;
import org.apache.tsfile.read.common.RowRecord;
import org.apache.tsfile.write.record.Tablet;

import java.util.Arrays;
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

    public static List<Map<String, Object>> toList(SessionDataSet dataSet) throws IoTDBConnectionException, StatementExecutionException {
        List<Map<String, Object>> results = Lists.newArrayList();
        List<String> columnNames = dataSet.getColumnNames();
        while (dataSet.hasNext()) {
            RowRecord rowRecord = dataSet.next();
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

    public static ValueTypeEnum valueType(String valueType) {
        return Arrays.stream(ValueTypeEnum.values()).filter(v -> v.getValue().equals(valueType)).findFirst().orElse(ValueTypeEnum.STRING);
    }

    public static TSDataType dataType(ValueTypeEnum valueType) {
        return switch (valueType) {
            case INT -> TSDataType.INT32;
            case LONG -> TSDataType.INT64;
            case BOOL -> TSDataType.BOOLEAN;
            case FLOAT -> TSDataType.FLOAT;
            case DOUBLE -> TSDataType.DOUBLE;
            default -> TSDataType.STRING;
        };
    }


    public static IotDbSave messageToInsert(PhecdaMessage message) {
        List<IotDbColumn> columns = Lists.newArrayList();
        columns.add(IotDbColumn.builder().name("deviceName").category(Tablet.ColumnCategory.TAG).dataType(org.apache.tsfile.enums.TSDataType.STRING).build());

        List<IotDbCell> cells = Lists.newArrayList();
        cells.add(IotDbCell.builder().name("deviceName").value(message.getDeviceName()).build());
        for (Map.Entry<String, PhecdaMessage.Reading> reading : message.getReadings().entrySet()) {
            var readingProperties = reading.getValue();
            if (Objects.nonNull(readingProperties)) {
                ValueTypeEnum valueType = valueType(readingProperties.getValueType());
                columns.add(IotDbColumn.builder().name(reading.getKey()).category(Tablet.ColumnCategory.FIELD).dataType(dataType(valueType)).build());
                cells.add(IotDbCell.builder().name(reading.getKey()).value(valueConvert(valueType, readingProperties.getReadingValue())).build());
            }
        }
        if (MapUtils.isNotEmpty(message.getTags())) {
            message.getTags().forEach((k, v) -> {
                columns.add(IotDbColumn.builder().name(k).category(Tablet.ColumnCategory.ATTRIBUTE).dataType(org.apache.tsfile.enums.TSDataType.STRING).build());
                cells.add(IotDbCell.builder().name(k).value(v).build());
            });
        }
        return IotDbSave.builder().table(message.getProductKey()).ts(message.getTs()).columns(columns).rows(List.of(cells)).build();
    }

}
