package com.trionesdev.phecda.foundation.core.domains.device.dao.impl;

import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.DevicePropertyDataCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.shared.model.IotDbQuery;
import com.trionesdev.phecda.foundation.core.domains.device.shared.model.IotDbSave;
import com.trionesdev.phecda.infrastructure.tsdb.TsDbTemplate;
import com.trionesdev.phecda.infrastructure.tsdb.schema.TsDbCell;
import com.trionesdev.phecda.infrastructure.tsdb.schema.TsDbQueryWrapper;
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
    private final TsDbTemplate tsDbTemplate;

    public TsDbQueryWrapper buildQueryWrapper(DevicePropertyDataCriteria criteria) {
        TsDbQueryWrapper wrapper = new TsDbQueryWrapper().table(criteria.getProductKey());
        wrapper.database(criteria.getProductKey()).eq("deviceName",criteria.getDeviceName());
        return wrapper;
    }

    public List<List<TsDbCell>> selectList(DevicePropertyDataCriteria criteria) {
        return tsDbTemplate.selecList(buildQueryWrapper(criteria));
    }

    public List<TsDbCell> selectLastList(DevicePropertyDataCriteria criteria) {
        return tsDbTemplate.selecList(buildQueryWrapper(criteria)).get(0);
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
