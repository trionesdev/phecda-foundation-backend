package ms.phecda.backend.rest.ssp.sdk.device.valuetype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ValueTypeEnum {
    INT(ValueTypeInt.class, "整数型"),
    FLOAT(ValueTypeFloat.class, "单精度浮点数"),
    DOUBLE(ValueTypeDouble.class, "双精度浮点数"),
    BOOL(ValueTypeBool.class, "布尔型"),
    STRING(ValueTypeString.class, "字符串"),

    STRUCT(ValueTypeStruct.class, "结构体"),
    ARRAY(ValueTypeArray.class, "数组");

    @Getter
    private final Class<? extends ValueType> clazz;

    @Getter
    private final String label;

}
