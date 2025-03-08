package com.trionesdev.phecda.foundation.core.domains.device.shared.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class IotDbCell {
    private String name;
    private Object value;
}
