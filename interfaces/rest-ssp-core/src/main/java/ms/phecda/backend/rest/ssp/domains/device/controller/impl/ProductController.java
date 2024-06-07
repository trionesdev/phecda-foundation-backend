package ms.phecda.backend.rest.ssp.domains.device.controller.impl;

import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.manager.dto.ProductExtDTO;
import ms.phecda.backend.core.domains.device.repository.criteria.ProductCriteria;
import ms.phecda.backend.core.domains.device.repository.po.ProductThingModelVersion;
import ms.phecda.backend.core.domains.device.service.impl.ProductService;
import ms.phecda.backend.rest.ssp.domains.device.support.RestProductConvertMapper;
import ms.phecda.backend.rest.ssp.internal.RestConstants;
import ms.phecda.backend.rest.ssp.sdk.device.ProductRest;
import ms.phecda.backend.rest.ssp.sdk.device.rep.ProductRep;
import ms.phecda.backend.rest.ssp.sdk.device.rep.ProductThingModelVersionRep;
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
        ProductThingModelVersion productThingModelVersion = productService.queryThingModel(productId, null).orElse(null);
        return RestProductConvertMapper.INSTANCE.from(productThingModelVersion);
    }
}
