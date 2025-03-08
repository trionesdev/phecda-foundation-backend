package com.trionesdev.phecda.infrastructure.tsdb.schema;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class TsDbInsertWrapper {
    private String tableName;
    private Instant timestamp;
    private List<TsDbColumn> columns;
    private List<List<Object>> rows;
}
