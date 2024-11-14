package com.trionesdev.phecda.foundation.core.domains.device.dao.criteria;

import com.trionesdev.commons.core.page.PageCriteria;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductDriverCriteria extends PageCriteria {
    private String name;
}
