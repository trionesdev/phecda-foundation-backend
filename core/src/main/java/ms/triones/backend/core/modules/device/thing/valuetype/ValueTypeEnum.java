package ms.triones.backend.core.modules.device.thing.valuetype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ValueTypeEnum {
    BOOL(ValueTypeBool.class, "布尔型"),
    STRING(ValueTypeString.class, "字符串"),
    INT(null, "整数型"),
    FLOAT(null, "单精度浮点数"),
    DOUBLE(null, "双精度浮点数"),
    STRUCT(null, "结构体");

    @Getter
    private final Class<? extends ValueType> clazz;

    @Getter
    private final String label;

}
