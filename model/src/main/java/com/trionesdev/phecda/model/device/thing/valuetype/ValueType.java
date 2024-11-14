package com.trionesdev.phecda.model.device.thing.valuetype;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 值类型
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "valueType",
        visible = true
)
@JsonSubTypes(
        {
                @JsonSubTypes.Type(value = ValueTypeInt.class, name = "INT"),
                @JsonSubTypes.Type(value = ValueTypeLong.class, name = "LONG"),
                @JsonSubTypes.Type(value = ValueTypeFloat.class, name = "FLOAT"),
                @JsonSubTypes.Type(value = ValueTypeDouble.class, name = "DOUBLE"),
                @JsonSubTypes.Type(value = ValueTypeBool.class, name = "BOOL"),
                @JsonSubTypes.Type(value = ValueTypeString.class, name = "STRING"),
                @JsonSubTypes.Type(value = ValueTypeStruct.class, name = "STRUCT"),
                @JsonSubTypes.Type(value = ValueTypeArray.class, name = "ARRAY"),
        }
)
public abstract class ValueType {
    private ValueTypeEnum valueType;
}
