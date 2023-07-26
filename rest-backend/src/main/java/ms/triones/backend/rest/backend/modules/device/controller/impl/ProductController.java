package ms.triones.backend.rest.backend.modules.device.controller.impl;

import com.moensun.commons.core.page.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.device.dao.criteria.ProductCriteria;
import ms.triones.backend.core.modules.device.dao.entity.Product;
import ms.triones.backend.core.modules.device.dao.entity.ProductThingModelDraft;
import ms.triones.backend.core.modules.device.dao.entity.ProductThingModelVersion;
import ms.triones.backend.core.modules.device.dao.entity.enums.ProductStatusEnum;
import ms.triones.backend.core.modules.device.service.bo.ThingModelUpsertBO;
import ms.triones.backend.core.modules.device.service.impl.ProductService;
import ms.triones.backend.core.modules.device.thing.valuetype.ValueTypeOption;
import ms.triones.backend.rest.backend.modules.device.controller.query.ProductQuery;
import ms.triones.backend.rest.backend.modules.device.controller.ro.ProductCreateRO;
import ms.triones.backend.rest.backend.modules.device.controller.ro.ProductProtocolPropertiesUpdateRO;
import ms.triones.backend.rest.backend.modules.device.controller.ro.ProductThingModelUpsertRO;
import ms.triones.backend.rest.backend.modules.device.controller.ro.ProductUpdateRO;
import ms.triones.backend.rest.backend.modules.device.support.DeviceConstants;
import ms.triones.backend.rest.backend.modules.device.support.DeviceRestConvertMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        product.setStatus(ProductStatusEnum.DEVELOPMENT);
        productService.createProduct(product);
    }

    @Operation(summary = "根据ID删除产品")
    @DeleteMapping(value = "products/{id}")
    public void deleteProductById(@PathVariable(value = "id") String id) {
        productService.deleteProductById(id);
    }

    @Operation(summary = "根据ID修改产品信息")
    @PutMapping(value = "products/{id}")
    public void updateProductById(
            @PathVariable(value = "id") String id,
            @Validated @RequestBody ProductUpdateRO args) {
        Product product = DeviceRestConvertMapper.INSTANT.from(args);
        product.setStatus(ProductStatusEnum.DEVELOPMENT);
        product.setId(id);
        productService.updateProductById(product);
    }

    @Operation(summary = "根据ID获取产品")
    @GetMapping(value = "products/{id}")
    public Product queryProductById(@PathVariable(value = "id") String id) {
        return productService.queryProductById(id).orElse(null);
    }

    @Operation(summary = "获取产品列表")
    @GetMapping(value = "products")
    public List<Product> queryProductList(ProductQuery query) {
        ProductCriteria criteria = DeviceRestConvertMapper.INSTANT.from(query);
        return productService.queryList(criteria);
    }

    @Operation(summary = "查询产品列表(分页)")
    @GetMapping(value = "products/page")
    public PageInfo<Product> queryProductPage(
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            ProductQuery query) {
        ProductCriteria criteria = DeviceRestConvertMapper.INSTANT.from(query);
        return productService.queryPage(pageNum, pageSize, criteria);
    }

    @Operation(summary = "获取物模型(草稿)")
    @GetMapping(value = "products/{productId}/thing-model-draft")
    public ProductThingModelDraft findProductThingModelDraft(@PathVariable(value = "productId") String productId) {
        return productService.findProductThingModelDraft(productId).orElse(null);
    }

    @Operation(summary = "新增物模型功能(草稿)")
    @PutMapping(value = "products/{productId}/thing-model-draft/upsert")
    public void upsertThingModelDraft(
            @PathVariable(value = "productId") String productId,
            @Validated @RequestBody ProductThingModelUpsertRO args) {
        ThingModelUpsertBO thingModelUpsertBO = DeviceRestConvertMapper.INSTANT.from(args);
        productService.upsertThingModel(productId, thingModelUpsertBO);
    }

    @Operation(summary = "删除物模型功能(草稿)")
    @DeleteMapping(value = "products/{productId}/thing-model-draft/abilities/{identifier}")
    public void deleteThingModel(
            @PathVariable(value = "productId") String productId,
            @PathVariable(value = "identifier") String identifier
    ) {
        productService.deleteThingModel(productId, identifier);
    }

    @Operation(summary = "发布物模型(草稿)")
    @PutMapping(value = "products/{productId}/thing-model-draft/publish")
    public void publishThingModelDraft(@PathVariable(value = "productId") String productId) {
        productService.publishThingModel(productId);
    }

    @Operation(summary = "获取产品物模型")
    @GetMapping(value = "products/{productId}/thing-model")
    public ProductThingModelVersion queryThingModel(
            @PathVariable(value = "productId") String productId,
            @RequestParam(value = "version", required = false) String version) {
        return productService.queryThingModel(productId, version).orElse(null);
    }

    @Operation(summary = "根据ID修改协议属性")
    @PutMapping(value = "products/{productId}/protocol-properties")
    public void updateProductProtocolProperties(
            @PathVariable(value = "productId") String productId,
            @RequestBody ProductProtocolPropertiesUpdateRO args) {
        productService.updateProductById(Product.builder()
                .id(productId)
                .protocolProperties(args.getProtocolProperties())
                .build());
    }

    @Operation(summary = "发布产品")
    @PutMapping(value = "products/{productId}/publish")
    public void publishProduct(@PathVariable(value = "productId") String productId) {
        productService.publishProduct(productId);
    }

    @Operation(summary = "撤销发布产品")
    @PutMapping(value = "products/{productId}/unpublish")
    public void revokePublishProduct(@PathVariable(value = "productId") String productId) {
        productService.revokePublishProduct(productId);
    }
}
