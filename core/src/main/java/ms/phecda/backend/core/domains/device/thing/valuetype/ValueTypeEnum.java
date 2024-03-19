package ms.phecda.backend.core.domains.device.thing.valuetype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ValueTypeEnum {
    INT(ValueTypeInt.class, "整数型"),
    LONG(ValueTypeLong.class, "长整数型"),
    FLOAT(ValueTypeFloat.class, "单精度浮点数"),
    DOUBLE(ValueTypeDouble.class, "双精度浮点数"),
    BOOL(ValueTypeBool.class, "布尔型"),
    STRING(ValueTypeString.class, "字符串"),

    STRUCT(ValueTypeStruct.class, "结构体"),
    ARRAY(ValueTypeArray.class, "数组");

    private final Class<? extends ValueType> clazz;

    private final String label;

}
