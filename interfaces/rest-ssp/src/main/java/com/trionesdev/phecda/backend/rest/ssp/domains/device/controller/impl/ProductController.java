package com.trionesdev.phecda.backend.rest.ssp.domains.device.controller.impl;

import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.backend.core.domains.device.dto.ProductExtDTO;
import com.trionesdev.phecda.backend.core.domains.device.dao.criteria.ProductCriteria;
import com.trionesdev.phecda.backend.core.domains.device.service.impl.ProductService;
import com.trionesdev.phecda.backend.rest.ssp.domains.device.support.RestProductConvertMapper;
import com.trionesdev.phecda.backend.rest.ssp.internal.RestConstants;
import com.trionesdev.phecda.backend.rest.ssp.sdk.device.ProductRest;
import com.trionesdev.phecda.backend.rest.ssp.sdk.device.rep.ProductRep;
import com.trionesdev.phecda.backend.rest.ssp.sdk.device.rep.ProductThingModelVersionRep;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController(value = "phecda.ssp.ProductController")
@RequestMapping(value = RestConstants.CONTEXT_PATH)
public class ProductController implements ProductRest {
    private final ProductService productService;

    @Override
    public List<ProductRep> findAll() {
        ProductCriteria criteria = ProductCriteria.builder().build();
        List<ProductExtDTO> products = productService.queryList(criteria);
        return RestProductConvertMapper.INSTANCE.from(products);
    }

    @Override
    public ProductThingModelVersionRep queryThingModel(String productId) {
        var thingModel = productService.queryThingModel(productId, null).orElse(null);
        return null;
    }
}
