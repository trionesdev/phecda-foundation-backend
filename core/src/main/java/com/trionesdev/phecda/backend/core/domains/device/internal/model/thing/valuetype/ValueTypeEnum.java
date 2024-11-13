package com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.valuetype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ValueTypeEnum {
    INT(ValueTypeInt.class, "Int","整数型"),
    LONG(ValueTypeLong.class, "Long","长整数型"),
    FLOAT(ValueTypeFloat.class, "Float","单精度浮点数"),
    DOUBLE(ValueTypeDouble.class, "Double","双精度浮点数"),
    BOOL(ValueTypeBool.class, "Bool","布尔型"),
    STRING(ValueTypeString.class, "String","字符串"),

    STRUCT(ValueTypeStruct.class, "Struct","结构体"),
    ARRAY(ValueTypeArray.class, "Array","数组");

    private final Class<? extends ValueType> clazz;
    private final String value;
    private final String label;

}
