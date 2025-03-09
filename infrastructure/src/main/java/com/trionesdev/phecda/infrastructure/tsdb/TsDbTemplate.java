package com.trionesdev.phecda.infrastructure.tsdb;

import java.util.List;
import com.trionesdev.phecda.infrastructure.tsdb.schema.TsDbCell;
import com.trionesdev.phecda.infrastructure.tsdb.schema.TsDbColumn;
import com.trionesdev.phecda.infrastructure.tsdb.schema.TsDbInsertWrapper;
import com.trionesdev.phecda.infrastructure.tsdb.schema.TsDbQueryWrapper;

public interface TsDbTemplate {
    void createDatabase(String database);
    void createTable(String table, List<TsDbColumn> columns);
    void save(TsDbInsertWrapper wrapper);
    List<List<TsDbCell>> selecList(TsDbQueryWrapper wrapper);
}
