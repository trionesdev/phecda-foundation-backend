package com.trionesdev.phecda.foundation.core.domains.device.shared.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class IotDbSave {
    private String table;
    private long ts;
    private List<IotDbColumn> columns;
    private List<List<IotDbCell>> rows;
}
