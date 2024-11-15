package com.trionesdev.phecda.foundation.core.domains.device.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.phecda.foundation.core.domains.device.internal.enums.ProductStatus;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.device.dto.ProductThingModelProfileDTO;
import com.trionesdev.phecda.foundation.core.domains.device.dto.ProductThingModelUpsertCmd;
import com.trionesdev.phecda.foundation.core.domains.device.internal.DeviceDomainConvert;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.ProductCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.dvo.ProductStatisticsDVO;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.ProductPO;
import com.trionesdev.phecda.foundation.core.domains.device.dto.ProductExtDTO;
import com.trionesdev.phecda.foundation.core.domains.device.internal.aggregate.entity.Product;
import com.trionesdev.phecda.model.device.thing.ThingModel;
import com.trionesdev.phecda.foundation.core.domains.device.internal.util.ProductUtils;
import com.trionesdev.phecda.foundation.core.domains.device.manager.impl.ProductManager;
import com.trionesdev.phecda.foundation.core.domains.device.manager.impl.ProductThingModelVersionManager;
import com.trionesdev.phecda.foundation.core.domains.device.internal.util.DeviceUtils;
import com.trionesdev.phecda.model.device.thing.ThingModelProperty;
import com.trionesdev.phecda.model.device.thing.valuetype.ValueTypeEnum;
import com.trionesdev.phecda.model.device.thing.valuetype.ValueTypeOption;
import com.trionesdev.phecda.foundation.core.domains.device.dto.ProductDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final DeviceDomainConvert convert;
    private final ProductManager productManager;
    private final ProductThingModelVersionManager productThingModelVersionManager;

    public List<ValueTypeOption> valueTypeOptions() {
        List<ValueTypeOption> options = Lists.newArrayList();
        for (ValueTypeEnum valueType : ValueTypeEnum.values()) {
            options.add(ValueTypeOption.builder().label(valueType.getLabel()).value(valueType.name()).build());
        }
        return options;
    }

    public ProductStatisticsDVO queryStatistics() {
        return productManager.queryStatistics();
    }

    public void createProduct(Product product) {
        if (StrUtil.isBlank(product.getKey())) {
            product.setKey(generateProductKey());
        }
        productManager.create(product);
    }


    public void deleteProductById(String id) {
        productManager.deleteById(id);
    }

    @Transactional
    public void updateProductById(Product product) {
        product.setStatus(ProductStatus.DEVELOPMENT);
        productManager.updateById(product);
    }


    public Optional<ProductDTO> queryProductById(String id) {
        return productManager.findById(id).map(this::assemble);
    }


    public Optional<ProductDTO> findProductByKey(String key) {
        return productManager.findByKey(key).map(this::assemble);
    }

    private List<ProductDTO> assembleProducts(Collection<Product> products) {
        if (CollectionUtil.isEmpty(products)) {
            return Collections.emptyList();
        }
        return products.stream().map(product -> {
            return convert.productEntityToDto(product);
        }).collect(Collectors.toList());
    }

    public List<ProductDTO> queryList(ProductCriteria criteria) {
        return assembleProducts(productManager.queryList(criteria));
    }

    public PageInfo<ProductExtDTO> queryPage(Integer pageNum, Integer pageSize, ProductCriteria criteria) {
        return productManager.queryPage(pageNum, pageSize, criteria);
    }

    public List<ProductDTO> findProductsByKeys(Collection<String> keys) {
        return assembleProducts(productManager.findProductsByKeys(keys));
    }


    public Optional<ThingModel> findProductThingModelDraft(String productId) {
        return productManager.findThingModelDraft(productId);
    }

    public void upsertThingModel(String productId, ProductThingModelUpsertCmd upsertCmd) {
        productManager.upsertThingModel(productId, upsertCmd);
    }

    public void deleteThingModel(String productId, String identifier) {
        productManager.removeThingModelAbility(productId, identifier);
    }

    /**
     * 发布物模型
     *
     * @param productId
     */
    public void releaseThingModel(String productId) {
        productManager.releaseThingModel(productId);
    }

    public List<ThingModelProperty> queryThingModelLatestPropertiesByProductKey(String productKey) {
        return productManager.findLatestThingModelPropertiesByProductKey(productKey);
    }

    /***
     * 查询最新物模型属性
     * @param productKey
     * @param identifier
     * @return
     */
    public Optional<ThingModelProperty> queryThingModelLatestProperty(String productKey, String identifier) {
        return productManager.findLatestThingModelPropertyByProductKey(productKey, identifier);
    }

    public Optional<ThingModel> queryThingModel(String productId, String version) {
        return productManager.findThingModel(productId, version);
    }

    public Optional<ThingModel> queryThingModelByKey(String key, String version) {
        return productManager.findThingModelByKey(key, version);
    }

    public void publishProduct(String productId) {
        productManager.publish(productId);
    }

    public void revokePublishProduct(String productId) {
        productManager.revokePublish(productId);
    }

    public String generateProductKey() {
        String key = DeviceUtils.productKeyGenerate();
        while (productManager.findByKey(key).isEmpty()) {
            key = DeviceUtils.productKeyGenerate();
        }
        return key;
    }

    private ProductDTO assemble(Product product) {
        if (Objects.isNull(product)) {
            return null;
        }
        return convert.productEntityToDto(product);
    }

    private List<ProductDTO> assembleProducts(List<ProductPO> records) {
        if (CollectionUtil.isEmpty(records)) {
            return Collections.emptyList();
        }
        return records.stream().map((product) -> {
            return convert.poToDto(product);
        }).collect(Collectors.toList());
    }

    public Optional<ProductThingModelProfileDTO> productProfile(String id) {
        return productManager.findById(id).map(ProductUtils::toProductProfile);
    }

}
