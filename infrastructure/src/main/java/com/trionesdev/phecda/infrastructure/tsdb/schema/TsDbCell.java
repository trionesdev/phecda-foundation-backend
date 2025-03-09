package com.trionesdev.phecda.infrastructure.tsdb.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TsDbCell {
    private String columnName;
    private DataType dataType;
    private Object value;
}
