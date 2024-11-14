package com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductProfileVO {
    private String json;
    private String yaml;
}
