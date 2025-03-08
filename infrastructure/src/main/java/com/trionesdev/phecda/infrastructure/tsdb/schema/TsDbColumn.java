package com.trionesdev.phecda.infrastructure.tsdb.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TsDbColumn {
    private String name;
    private ColumnCategory category;
    private DataType dataType;
}
