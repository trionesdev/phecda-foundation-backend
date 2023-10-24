package ms.phecda.rest.ssp.modules.device.controller.impl;

import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.dao.criteria.ProductCriteria;
import ms.phecda.backend.core.domains.device.dao.entity.Product;
import ms.phecda.backend.core.domains.device.dao.entity.ProductThingModelVersion;
import ms.phecda.backend.core.domains.device.service.impl.ProductService;
import ms.phecda.rest.ssp.modules.device.support.RestProductConvertMapper;
import ms.phecda.rest.ssp.sdk.device.ProductRest;
import ms.phecda.rest.ssp.sdk.device.rep.ProductRep;
import ms.phecda.rest.ssp.sdk.device.rep.ProductThingModelVersionRep;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static ms.phecda.rest.ssp.support.RestConstants.CONTEXT_PATH;

@RequiredArgsConstructor
@RestController(value = "phecda.ssp.ProductController")
@RequestMapping(value = CONTEXT_PATH)
public class ProductController implements ProductRest {
    private final ProductService productService;

    @Override
    public List<ProductRep> findAll() {
        ProductCriteria criteria = ProductCriteria.builder().build();
        List<Product> products = productService.queryList(criteria);
        return RestProductConvertMapper.INSTANCE.from(products);
    }

    @Override
    public ProductThingModelVersionRep queryThingModel(String productId) {
        ProductThingModelVersion productThingModelVersion = productService.queryThingModel(productId, null).orElse(null);
        return RestProductConvertMapper.INSTANCE.from(productThingModelVersion);
    }
}
