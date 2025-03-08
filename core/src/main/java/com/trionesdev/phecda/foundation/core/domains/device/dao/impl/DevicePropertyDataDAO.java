package com.trionesdev.phecda.foundation.core.domains.device.dao.impl;

import com.trionesdev.phecda.foundation.core.domains.device.shared.model.IotDbQuery;
import com.trionesdev.phecda.foundation.core.domains.device.shared.model.IotDbSave;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.device.internal.util.IotDbUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.iotdb.isession.ITableSession;
import org.apache.iotdb.isession.SessionDataSet;
import org.apache.iotdb.isession.pool.ITableSessionPool;
import org.apache.iotdb.isession.pool.SessionDataSetWrapper;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.pool.SessionPool;
import org.apache.tsfile.read.common.RowRecord;
import org.apache.tsfile.write.record.Tablet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class DevicePropertyDataDAO {
    private final ITableSessionPool tableSessionPool;
//    private final SessionPool sessionPool;

//    public void insertRecord(String path, long time, List<String> measurements, List<TSDataType> types, List<Object> values) {
//        try {
//            sessionPool.insertRecord(path, time, measurements, types, values);
//        } catch (IoTDBConnectionException | StatementExecutionException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void insertRecord(String path, long time, List<String> measurements, List<String> values) {
//        try {
//            sessionPool.insertRecord(path, time, measurements, values);
//        } catch (IoTDBConnectionException | StatementExecutionException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public List<Map<String, Object>> selectList(IotDbQuery query) {
//        try {
//            SessionDataSetWrapper sessionDataSet = sessionPool.executeRawDataQuery(paths, startTime, endTime, 6000);
//            return IotDbUtils.toList(sessionDataSet);
//        } catch (IoTDBConnectionException | StatementExecutionException e) {
//            throw new RuntimeException(e);
//        }
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * from ").append(query.getProductKey());
        sb.append(" WHERE deviceName = '").append(query.getDeviceName()).append("'");
        if (query.getStartTime() > 0) {
            sb.append(" AND startTime > ").append(query.getStartTime());
        }
        if (query.getEndTime() > 0) {
            sb.append(" AND endTime < ").append(query.getEndTime());
        }
        sb.append(" ORDER BY time DESC limit 6000");
        try (ITableSession session = tableSessionPool.getSession()) {
            try (SessionDataSet dataSet = session.executeQueryStatement(sb.toString())) {
                return IotDbUtils.toList(dataSet);
            } catch (StatementExecutionException e) {
                throw new RuntimeException(e);
            }
        } catch (IoTDBConnectionException e) {
            throw new RuntimeException(e);
        } finally {
            tableSessionPool.close();
        }
    }

    public List<Map<String, Object>> selectLastList(IotDbQuery query) {
//        try {
//            SessionDataSetWrapper sessionDataSet = sessionPool.executeLastDataQuery(paths);
//            return IotDbUtils.toList(sessionDataSet);
//        } catch (IoTDBConnectionException | StatementExecutionException e) {
//            throw new RuntimeException(e);
//        }
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * from ").append(query.getProductKey());
        sb.append(" WHERE deviceName = '").append(query.getDeviceName()).append("'");
        sb.append(" ORDER BY time DESC limit 1");
        try (ITableSession session = tableSessionPool.getSession()) {
            try (SessionDataSet dataSet = session.executeQueryStatement(sb.toString())) {
                return IotDbUtils.toList(dataSet);
            } catch (StatementExecutionException e) {
                throw new RuntimeException(e);
            }
        } catch (IoTDBConnectionException e) {
            throw new RuntimeException(e);
        } finally {
            tableSessionPool.close();
        }
    }

    public void saveBatch(IotDbSave data) {

        if (CollectionUtils.isNotEmpty(data.getColumns())) {
            List<String> columns = new ArrayList<>();
            List<Tablet.ColumnCategory> categories = new ArrayList<>();
            List<org.apache.tsfile.enums.TSDataType> dataTypes = new ArrayList<>();

            if (CollectionUtils.isNotEmpty(data.getColumns())) {
                data.getColumns().forEach(column -> {
                    columns.add(column.getName());
                    categories.add(column.getCategory());
                    dataTypes.add(column.getDataType());
                });
            }

            try (ITableSession session = tableSessionPool.getSession()) {
                Tablet tablet = new Tablet(data.getTable(), columns, dataTypes, categories, data.getRows().size());
                if (CollectionUtils.isNotEmpty(data.getRows())) {
                    data.getRows().forEach(row -> {
                        int rowIndex = tablet.getRowSize();
                        tablet.addTimestamp(rowIndex, data.getTs());
                        if (CollectionUtils.isNotEmpty(row)) {
                            row.forEach(cell -> {
                                tablet.addValue(cell.getName(), rowIndex, cell.getValue());
                            });
                        }
                    });
                }
                session.insert(tablet);
                tablet.reset();
            } catch (IoTDBConnectionException | StatementExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
