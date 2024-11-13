package com.trionesdev.phecda.backend.core.domains.device.dao.criteria;

import com.trionesdev.commons.core.page.PageCriteria;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductDriverCriteria extends PageCriteria {
    private String name;
}
