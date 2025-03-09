package com.trionesdev.phecda.foundation.core.domains.device.internal.util;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.trionesdev.phecda.foundation.core.domains.device.internal.aggregate.entity.DevicePropertyData;
import com.trionesdev.phecda.infrastructure.tsdb.schema.*;
import com.trionesdev.phecda.model.device.PhecdaMessage;
import com.trionesdev.phecda.model.device.thing.valuetype.ValueTypeEnum;
import org.apache.commons.collections4.MapUtils;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static cn.hutool.core.util.RandomUtil.BASE_CHAR;

public class DeviceUtils {
    public static final String BASE_CHAR_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String productKeyGenerate() {
        return RandomUtil.randomString(BASE_CHAR , 1) + RandomUtil.randomString(9);
    }

    public static ValueTypeEnum valueType(String valueType) {
        return Arrays.stream(ValueTypeEnum.values()).filter(v -> v.getValue().equals(valueType)).findFirst().orElse(ValueTypeEnum.STRING);
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

    public static DataType dataType(ValueTypeEnum valueType) {
        return switch (valueType) {
            case INT -> DataType.INT;
            case LONG -> DataType.LONG;
            case BOOL -> DataType.BOOLEAN;
            case FLOAT -> DataType.FLOAT;
            case DOUBLE -> DataType.DOUBLE;
            default -> DataType.STRING;
        };
    }

    public static DevicePropertyData messageToInsert(PhecdaMessage message) {
        List<TsDbColumn> columns = Lists.newArrayList();
        columns.add(TsDbColumn.builder().name("deviceName").category(ColumnCategory.TAG).dataType(DataType.STRING).build());

        List<TsDbCell> cells = Lists.newArrayList();
        cells.add(TsDbCell.builder().columnName("deviceName").value(message.getDeviceName()).build());
        for (Map.Entry<String, PhecdaMessage.Reading> reading : message.getReadings().entrySet()) {
            var readingProperties = reading.getValue();
            if (Objects.nonNull(readingProperties)) {
                ValueTypeEnum valueType = valueType(readingProperties.getValueType());
                columns.add(TsDbColumn.builder().name(reading.getKey()).category(ColumnCategory.FIELD).dataType(dataType(valueType)).build());
                cells.add(TsDbCell.builder().columnName(reading.getKey()).value(valueConvert(valueType, readingProperties.getReadingValue())).build());
            }
        }
        if (MapUtils.isNotEmpty(message.getTags())) {
            message.getTags().forEach((k, v) -> {
                columns.add(TsDbColumn.builder().name(k).category(ColumnCategory.ATTRIBUTE).dataType(DataType.STRING).build());
                cells.add(TsDbCell.builder().columnName(k).value(v).build());
            });
        }
        return DevicePropertyData.builder().productKey(message.getProductKey()).deviceName(message.getDeviceName()).timestamp(Instant.ofEpochMilli(message.getTs())).columns(columns).rows(List.of(cells)).build();
    }

}
