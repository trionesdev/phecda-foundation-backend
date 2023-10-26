package ms.phecda.rest.ssp.sdk.device.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ms.phecda.rest.ssp.sdk.device.valuetype.ValueTypeEnum;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum ReadingValueTypeEnum {
    INT("Int", ValueTypeEnum.INT, null),
    FLOAT("Float", ValueTypeEnum.FLOAT, null),
    DOUBLE("Double", ValueTypeEnum.DOUBLE, null),
    BOOL("Bool", ValueTypeEnum.BOOL, null),
    STRING("String", ValueTypeEnum.STRING, null),
    STRUCT("Struct", ValueTypeEnum.STRUCT, null),
    INT_ARRAY("IntArray", ValueTypeEnum.ARRAY, ValueTypeEnum.INT),
    FLOAT_ARRAY("FloatArray", ValueTypeEnum.ARRAY, ValueTypeEnum.FLOAT),
    DOUBLE_ARRAY("DoubleArray", ValueTypeEnum.ARRAY, ValueTypeEnum.DOUBLE),
    BOOL_ARRAY("BoolArray", ValueTypeEnum.ARRAY, ValueTypeEnum.BOOL),
    STRING_ARRAY("StringArray", ValueTypeEnum.ARRAY, ValueTypeEnum.STRING),
    STRUCT_ARRAY("StructArray", ValueTypeEnum.ARRAY, ValueTypeEnum.STRUCT);

    private final String val;
    private final ValueTypeEnum valueType;
    private final ValueTypeEnum childValueType;

    public static ReadingValueTypeEnum of(String val) {
        for (ReadingValueTypeEnum item : values()) {
            if (Objects.equals(item.getVal(), val)) {
                return item;
            }
        }
        return null;
    }
}
