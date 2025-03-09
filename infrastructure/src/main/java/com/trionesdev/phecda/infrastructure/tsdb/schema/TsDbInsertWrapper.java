package com.trionesdev.phecda.infrastructure.tsdb.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TsDbInsertWrapper {
    private String tableName;
    private Instant timestamp;
    private List<TsDbColumn> columns;
    private List<List<TsDbCell>> rows;
}
