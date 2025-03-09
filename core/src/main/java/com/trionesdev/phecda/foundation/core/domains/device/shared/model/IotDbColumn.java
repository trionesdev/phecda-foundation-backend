package com.trionesdev.phecda.foundation.core.domains.device.shared.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.tsfile.enums.TSDataType;
import org.apache.tsfile.write.record.Tablet;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class IotDbColumn {
    private String name;
    private Tablet.ColumnCategory category;
    private TSDataType dataType;
}
