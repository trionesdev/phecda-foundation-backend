package com.trionesdev.phecda.backend.core.domains.device.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.trionesdev.phecda.backend.core.domains.device.dao.po.ProductPO;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductExtDTO extends ProductPO {
    private String nodeTypeLabel;
    private String typeLabel;
}
