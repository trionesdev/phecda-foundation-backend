package com.trionesdev.phecda.foundation.core.domains.device.dao.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.trionesdev.phecda.foundation.core.domains.device.shared.enums.NodeType;

import java.util.Collection;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceCriteria {
    private String productId;
    private String nodeId;
    private String gatewayId;
    private String productKey;
    private NodeType nodeType;
    private String name;
    private String remarkName;

    private Collection<String> names;
    private Collection<String> ids;
}
