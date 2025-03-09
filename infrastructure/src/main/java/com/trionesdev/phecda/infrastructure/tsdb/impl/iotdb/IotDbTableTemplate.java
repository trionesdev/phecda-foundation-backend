package com.trionesdev.phecda.infrastructure.tsdb.impl.iotdb;

import com.trionesdev.phecda.infrastructure.tsdb.TsDbTemplate;
import com.trionesdev.phecda.infrastructure.tsdb.schema.TsDbCell;
import com.trionesdev.phecda.infrastructure.tsdb.schema.TsDbColumn;
import com.trionesdev.phecda.infrastructure.tsdb.schema.TsDbInsertWrapper;
import com.trionesdev.phecda.infrastructure.tsdb.schema.TsDbQueryWrapper;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.iotdb.isession.ITableSession;
import org.apache.iotdb.isession.SessionDataSet;
import org.apache.iotdb.isession.pool.ITableSessionPool;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.tsfile.write.record.Tablet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class IotDbTableTemplate implements TsDbTemplate {
    private final ITableSessionPool tableSessionPool;

    @Override
    public void createDatabase(String database) {
        try (ITableSession session = tableSessionPool.getSession()) {
            session.executeQueryStatement("create database " + database);
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createTable(String table, List<TsDbColumn> columns) {

    }

    @Override
    public void save(TsDbInsertWrapper wrapper) {
        if (CollectionUtils.isNotEmpty(wrapper.getColumns())) {
            List<String> columns = new ArrayList<>();
            List<Tablet.ColumnCategory> categories = new ArrayList<>();
            List<org.apache.tsfile.enums.TSDataType> dataTypes = new ArrayList<>();

            if (CollectionUtils.isNotEmpty(wrapper.getColumns())) {
                wrapper.getColumns().forEach(column -> {
                    columns.add(column.getName());
                    categories.add(IotDbUtils.getColumnCategory(column.getCategory()));
                    dataTypes.add(IotDbUtils.getDataType(column.getDataType()));
                });
            }

            try (ITableSession session = tableSessionPool.getSession()) {
                Tablet tablet = new Tablet(wrapper.getTableName(), columns, dataTypes, categories,
                        wrapper.getRows().size());
                if (CollectionUtils.isNotEmpty(wrapper.getRows())) {
                    wrapper.getRows().forEach(row -> {
                        if (row.size() != columns.size()) {
                            return;
                        }
                        int rowIndex = tablet.getRowSize();
                        tablet.addTimestamp(rowIndex, wrapper.getTimestamp().toEpochMilli());
                        for (int i = 0; i < row.size(); i++) {
                            tablet.addValue(columns.get(i), rowIndex, row.get(i));
                        }
                        ;
                    });

                }
                session.insert(tablet);
                tablet.reset();
            } catch (IoTDBConnectionException | StatementExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<List<TsDbCell>> selecList(TsDbQueryWrapper wrapper) {
        String sql = IotDbUtils.toSelectSql(wrapper);
        try (ITableSession session = tableSessionPool.getSession()) {
            try (SessionDataSet dataSet = session.executeQueryStatement(sql)) {
                return IotDbUtils.toList(dataSet);
            } catch (StatementExecutionException e) {
                throw new RuntimeException(e);
            }
        } catch (IoTDBConnectionException e) {
            throw new RuntimeException(e);
        }
    }
}
