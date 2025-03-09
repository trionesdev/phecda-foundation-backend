package com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.impl;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.phecda.foundation.core.domains.device.internal.aggregate.entity.Product;
import com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro.ProductCreateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro.ProductUpdateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro.ProtocolPropertiesUpdateRO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.model.device.thing.ThingModel;
import com.trionesdev.phecda.model.device.thing.valuetype.ValueTypeOption;
import com.trionesdev.phecda.foundation.core.domains.device.dto.ProductExtDTO;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.ProductCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.dvo.ProductStatisticsDVO;
import com.trionesdev.phecda.foundation.core.domains.device.service.impl.ProductService;
import com.trionesdev.phecda.foundation.core.domains.device.dto.ProductDTO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro.ProductQueryRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro.ProductThingModelUpsertRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.vo.ProductProfileVO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.device.internal.DeviceBeRestConvert;
import com.trionesdev.phecda.foundation.rest.tenant.domains.device.internal.DeviceConstants;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "产品")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = DeviceConstants.DEVICE_URI)
public class ProductController {
    private final DeviceBeRestConvert convert;
    private final ProductService productService;

    @Operation(summary = "产品统计")
    @GetMapping(value = "products/statistics")
    public ProductStatisticsDVO statistics() {
        return productService.queryStatistics();
    }

    @Operation(summary = "值类型选项")
    @GetMapping(value = "value-type/options")
    public List<ValueTypeOption> valueTypeOptions() {
        return productService.valueTypeOptions();
    }

    @Operation(summary = "新建产品")
    @PostMapping(value = "products")
    public void createProduct(@Validated @RequestBody ProductCreateRO args) {
        var product = convert.from(args);
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
        var product = convert.from(args);
        product.setId(id);
        productService.updateProductById(product);
    }

    @Operation(summary = "根据ID获取产品")
    @GetMapping(value = "products/{id}")
    public ProductDTO queryProductById(@PathVariable(value = "id") String id) {
        return productService.queryProductById(id).orElse(null);
    }

    @Operation(summary = "获取产品列表")
    @GetMapping(value = "products/list")
    public List<ProductDTO> queryProductList(ProductQueryRO query) {
        ProductCriteria criteria = convert.from(query);
        return productService.queryList(criteria);
    }

    @Operation(summary = "查询产品列表(分页)")
    @GetMapping(value = "products/page")
    public PageInfo<ProductExtDTO> queryProductPage(
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            ProductQueryRO query) {
        ProductCriteria criteria = DeviceBeRestConvert.INSTANT.from(query);
        return productService.queryPage(pageNum, pageSize, criteria);
    }

    @Operation(summary = "获取物模型(草稿)")
    @GetMapping(value = "products/{productId}/thing-model-draft")
    public ThingModel findProductThingModelDraft(@PathVariable(value = "productId") String productId) {
        return productService.findProductThingModelDraft(productId).orElse(null);
    }

    @Operation(summary = "新增物模型功能(草稿)")
    @PutMapping(value = "products/{productId}/thing-model-draft/upsert")
    public void upsertThingModelDraft(
            @PathVariable(value = "productId") String productId,
            @Validated @RequestBody ProductThingModelUpsertRO args) {
        var thingModelUpsertBO = convert.from(args);
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
    @PutMapping(value = "products/{productId}/thing-model-draft/release")
    public void releaseThingModelDraft(@PathVariable(value = "productId") String productId) {
        productService.releaseThingModel(productId);
    }

    @Operation(summary = "获取产品物模型")
    @GetMapping(value = "products/{productId}/thing-model")
    public ThingModel queryThingModel(
            @PathVariable(value = "productId") String productId,
            @RequestParam(value = "version", required = false) String version) {
        return productService.queryThingModel(productId, version).orElse(null);
    }

    @Operation(summary = "根据KEY获取产品物模型")
    @GetMapping(value = "products/key/{key}/thing-model")
    public ThingModel queryThingModelByKey(
            @PathVariable(value = "key") String key,
            @RequestParam(value = "version", required = false) String version) {
        return productService.queryThingModelByKey(key, version).orElse(null);
    }


    @Operation(summary = "根据ID修改协议属性")
    @PutMapping(value = "products/{productId}/protocol-properties")
    public void updateProductProtocolProperties(
            @PathVariable(value = "productId") String productId,
            @RequestBody ProtocolPropertiesUpdateRO args) {

        var product =  Product.builder().id(productId).protocolProperties(args.getProtocolProperties()).build();
        productService.updateProductById(product);
    }

    @Operation(summary = "发布产品")
    @PutMapping(value = "products/{productId}/release")
    public void releaseProduct(@PathVariable(value = "productId") String productId) {
        productService.releaseProduct(productId);
    }

    @Operation(summary = "撤销发布产品")
    @PutMapping(value = "products/{productId}/revoke")
    public void revokePublishProduct(@PathVariable(value = "productId") String productId) {
        productService.revokePublishProduct(productId);
    }

    @Operation(summary = "产品物模型配置文档")
    @GetMapping(value = "products/{id}/thing-model-profile")
    public ProductProfileVO productProfile(@PathVariable String id){
        return productService.productProfile(id).map(productProfileDTO -> {
            try {
                var objectMapper = new ObjectMapper();
                objectMapper.setSerializationInclusion(Include.NON_NULL);
                String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(productProfileDTO);
                var yamlObjectMapper = new ObjectMapper(new YAMLFactory());
                yamlObjectMapper.setSerializationInclusion(Include.NON_NULL);
                String yaml = yamlObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(productProfileDTO);
                return ProductProfileVO.builder().json(json).yaml(yaml).build();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        }).orElse(null);
    }

}
