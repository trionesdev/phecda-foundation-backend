package com.trionesdev.phecda.infrastructure.tsdb.impl.iotdb;

import static org.mockito.ArgumentMatchers.refEq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.iotdb.isession.SessionDataSet;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.tsfile.enums.TSDataType;
import org.apache.tsfile.read.common.Field;
import org.apache.tsfile.read.common.RowRecord;
import org.apache.tsfile.write.record.Tablet;
import org.checkerframework.checker.units.qual.C;

import com.google.common.collect.Lists;
import com.trionesdev.phecda.infrastructure.tsdb.schema.ColumnCategory;
import com.trionesdev.phecda.infrastructure.tsdb.schema.DataType;
import com.trionesdev.phecda.infrastructure.tsdb.schema.TsDbCell;
import com.trionesdev.phecda.infrastructure.tsdb.schema.TsDbQueryWrapper;

public class IotDbUtils {
    public static TSDataType getDataType(DataType dataType) {
        return switch (dataType) {
            case BOOLEAN -> TSDataType.BOOLEAN;
            case INT -> TSDataType.INT32;
            case LONG -> TSDataType.INT64;
            case FLOAT -> TSDataType.FLOAT;
            case DOUBLE -> TSDataType.DOUBLE;
            case STRING -> TSDataType.STRING;
            default -> TSDataType.STRING;
        };
    }

    public static Tablet.ColumnCategory getColumnCategory(ColumnCategory columnCategory) {
        return switch (columnCategory) {
            case TAG -> Tablet.ColumnCategory.TAG;
            case FIELD -> Tablet.ColumnCategory.FIELD;
            case ATTRIBUTE -> Tablet.ColumnCategory.ATTRIBUTE;
        };
    }

    public static DataType toTsDataType(TSDataType tsDataType) {
        return switch (tsDataType) {
            case BOOLEAN -> DataType.BOOLEAN;
            case INT32 -> DataType.INT;
            case INT64 -> DataType.LONG;
            case FLOAT -> DataType.FLOAT;
            case DOUBLE -> DataType.DOUBLE;
            case TEXT -> DataType.STRING;
            default -> DataType.STRING;
        };
    }

    public static DataType toTsDataType(String dataType) {
        TSDataType tsDataType = TSDataType.valueOf(dataType);
        return switch (tsDataType) {
            case BOOLEAN -> DataType.BOOLEAN;
            case INT32 -> DataType.INT;
            case INT64 -> DataType.LONG;
            case FLOAT -> DataType.FLOAT;
            case DOUBLE -> DataType.DOUBLE;
            case TEXT -> DataType.STRING;
            default -> DataType.STRING;
        };
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

    public static String toSql(TsDbQueryWrapper wrapper) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * from ").append(wrapper.getTableName());
        if (MapUtils.isNotEmpty(wrapper.getEqCriteria())
        || MapUtils.isNotEmpty(wrapper.getLtCriteria())
        || MapUtils.isNotEmpty(wrapper.getLeCriteria())
        || MapUtils.isNotEmpty(wrapper.getGtCriteria())
        || MapUtils.isNotEmpty(wrapper.getGeCriteria())
        ) {
            sb.append(" WHERE ");
            List<String> criteria = new ArrayList<>();
            if (MapUtils.isNotEmpty(wrapper.getEqCriteria())) {
                List<String> eqCriteria = new ArrayList<>();
                for (Map.Entry<String, Object> entry : wrapper.getEqCriteria().entrySet()) {
                    if (entry.getValue() instanceof String) {
                        eqCriteria.add( entry.getKey()+"='"+entry.getValue()+"'");
                    }else {
                        eqCriteria.add(entry.getKey() + " = " + entry.getValue();
                    }

                }
                criteria.add(StringUtils.join(eqCriteria, ","));
            }
        }
        sb.append(" ORDER BY time DESC ");
        return sb.toString();
    }

    public static List<List<TsDbCell>> toList(SessionDataSet dataSet)
            throws IoTDBConnectionException, StatementExecutionException {
        List<List<TsDbCell>> results = Lists.newArrayList();
        List<String> columnNames = dataSet.getColumnNames();
        List<String> columnTypes = dataSet.getColumnTypes();
        while (dataSet.hasNext()) {
            RowRecord rowRecord = dataSet.next();
            List<TsDbCell> row = new ArrayList<>();
            if (!rowRecord.getFields().isEmpty()) {
                for (int i = 0; i < rowRecord.getFields().size(); i++) {
                    // row.put(columnNames.get(i), fieldValue(rowRecord.getFields().get(i)));
                    row.add(
                            TsDbCell.builder().columnName(columnNames.get(i))
                                    .dataType(toTsDataType(columnTypes.get(i)))
                                    .value(fieldValue(rowRecord.getFields().get(i)))
                                    .build());
                }
            }
            results.add(row);
        }
        return results;
    }

}
