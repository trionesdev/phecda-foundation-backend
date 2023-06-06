package ms.triones.backend.rest.backend.modules.device.controller.impl;

import com.moensun.commons.core.page.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.device.dao.criteria.ProductCriteria;
import ms.triones.backend.core.modules.device.dao.entity.Product;
import ms.triones.backend.core.modules.device.dao.entity.ProductThingModelDraft;
import ms.triones.backend.core.modules.device.service.bo.ThingModelUpsertBO;
import ms.triones.backend.core.modules.device.service.impl.ProductService;
import ms.triones.backend.core.modules.device.thing.valuetype.ValueTypeOption;
import ms.triones.backend.rest.backend.modules.device.controller.ro.ProductCreateRO;
import ms.triones.backend.rest.backend.modules.device.controller.ro.ProductThingModelUpsertRO;
import ms.triones.backend.rest.backend.modules.device.controller.ro.ProductUpdateRO;
import ms.triones.backend.rest.backend.modules.device.support.DeviceConstants;
import ms.triones.backend.rest.backend.modules.device.support.DeviceRestConvertMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "产品")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = DeviceConstants.DEVICE_URI)
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "值类型选项")
    @GetMapping(value = "value-type/options")
    public List<ValueTypeOption> valueTypeOptions() {
        return productService.valueTypeOptions();
    }

    @Operation(summary = "新建产品")
    @PostMapping(value = "products")
    public void createProduct(@Validated @RequestBody ProductCreateRO args) {
        Product product = DeviceRestConvertMapper.INSTANT.from(args);
        productService.createProduct(product);
    }

    @Operation(summary = "根据ID删除产品")
    @DeleteMapping(value = "products/{id}")
    public void deleteProductById(
            @PathVariable(value = "id") String id
    ) {
        productService.deleteProductById(id);
    }

    @Operation(summary = "根据ID修改产品信息")
    @PutMapping(value = "products/{id}")
    public void updateProductById(
            @PathVariable(value = "id") String id,
            @Validated @RequestBody ProductUpdateRO args
    ) {
        Product product = DeviceRestConvertMapper.INSTANT.from(args);
        product.setId(id);
    }

    @Operation(summary = "根据ID获取产品")
    @GetMapping(value = "products/{id}")
    public Product queryProductById(
            @PathVariable(value = "id") String id
    ) {
        return productService.queryProductById(id).orElse(null);
    }

    @Operation(summary = "查询产品列表(分页)")
    @GetMapping(value = "products/page")
    public PageInfo<Product> queryProductPage(
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize
    ) {
        return productService.queryPage(pageNum, pageSize, ProductCriteria.builder().build());
    }

    @Operation(summary = "获取物模型草稿")
    @GetMapping(value = "products/{productId}/thing-model")
    public ProductThingModelDraft findProductThingModelDraft(
            @PathVariable(value = "productId") String productId
    ) {
        return productService.findProductThingModelDraft(productId).orElse(null);
    }

    @Operation(summary = "新增物模型功能")
    @PutMapping(value = "products/{productId}/thing-model/upsert")
    public void upsertThingModelDraft(
            @PathVariable(value = "productId") String productId,
            @Validated @RequestBody ProductThingModelUpsertRO args
    ) {
        ThingModelUpsertBO thingModelUpsertBO = DeviceRestConvertMapper.INSTANT.from(args);
        productService.upsertThingModel(productId, thingModelUpsertBO);
    }

    @Operation(summary = "发布物模型")
    @PutMapping(value = "products/{productId}/thing-model/publish")
    public void publishThingModelDraft(
            @PathVariable(value = "productId") String productId
    ) {
    }

}
