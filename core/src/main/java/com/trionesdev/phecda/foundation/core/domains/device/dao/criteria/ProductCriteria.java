package com.trionesdev.phecda.foundation.core.domains.device.dao.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.trionesdev.phecda.foundation.core.domains.device.shared.enums.NodeType;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCriteria {
    private String name;
    private NodeType nodeType;
}
