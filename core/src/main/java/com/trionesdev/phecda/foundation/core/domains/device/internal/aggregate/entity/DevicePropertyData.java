package com.trionesdev.phecda.foundation.core.domains.device.internal.aggregate.entity;

import com.trionesdev.phecda.infrastructure.tsdb.schema.TsDbCell;
import com.trionesdev.phecda.infrastructure.tsdb.schema.TsDbColumn;
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
public class DevicePropertyData {
    private Instant timestamp;
    private String productKey;
    private String deviceName;
    private List<TsDbColumn> columns;
    private List<List<TsDbCell>> rows;
}
