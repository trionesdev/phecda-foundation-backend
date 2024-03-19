package ms.phecda.backend.rest.ssp.sdk.device;

import ms.phecda.backend.rest.ssp.sdk.device.rep.ProductRep;
import ms.phecda.backend.rest.ssp.sdk.device.rep.ProductThingModelVersionRep;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ProductRest {
    String PRODUCT_URI = "product/";

    @GetMapping(PRODUCT_URI + "products")
    List<ProductRep> findAll();

    @GetMapping(value = "products/{productId}/thing-model")
    ProductThingModelVersionRep queryThingModel(@PathVariable(value = "productId") String productId);
}
