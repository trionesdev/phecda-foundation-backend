package com.trionesdev.phecda.foundation.core.domains.device.manager.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.core.util.PageUtils;
import com.trionesdev.commons.exception.NotFoundException;
import com.trionesdev.phecda.foundation.core.domains.device.internal.DeviceDomainConvert;
import com.trionesdev.phecda.foundation.core.domains.device.internal.util.ProductUtils;
import com.trionesdev.phecda.infrastructure.tsdb.TsDbTemplate;
import com.trionesdev.phecda.infrastructure.tsdb.schema.TsDbColumn;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.device.dto.ProductThingModelUpsertCmd;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.ProductCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.dvo.ProductStatisticsDVO;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.ProductPO;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.ProductThingModelVersion;
import com.trionesdev.phecda.foundation.core.domains.device.internal.aggregate.entity.Product;
import com.trionesdev.phecda.foundation.core.domains.device.shared.enums.NodeType;
import com.trionesdev.phecda.foundation.core.domains.device.shared.enums.ProductStatus;
import com.trionesdev.phecda.foundation.core.domains.device.dao.impl.ProductDAO;
import com.trionesdev.phecda.foundation.core.domains.device.dao.impl.ProductThingModelVersionDAO;
import com.trionesdev.phecda.foundation.core.domains.device.shared.enums.ProductType;
import com.trionesdev.phecda.foundation.core.domains.device.dto.ProductExtDTO;
import com.trionesdev.phecda.model.device.thing.ThingModel;
import com.trionesdev.phecda.model.device.thing.ThingModelProperty;
import com.trionesdev.phecda.foundation.core.domains.device.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.trionesdev.phecda.foundation.core.domains.device.internal.DeviceCacheConstants.*;
import static com.trionesdev.phecda.foundation.core.domains.device.internal.DeviceErrors.PRODUCT_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class ProductManager {
    private final DeviceDomainConvert convert;
    private final ProductDAO productDAO;
    private final ProductThingModelVersionDAO productThingModelVersionDAO;
    private final ProductRepository productRepository;
    private final TsDbTemplate tsDbTemplate;

    public ProductStatisticsDVO queryStatistics() {
        return productDAO.selectStatistics();
    }

    public void create(Product product) {
        if (Objects.equals(NodeType.GATEWAY, product.getNodeType())) {
            product.setType(ProductType.GATEWAY);
        }
        product.setStatus(ProductStatus.DEVELOPMENT);
        productRepository.save(product);
    }

    public void deleteById(String id) {
        var product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("PRODUCT_NOT_FOUND"));
        productRepository.deleteProduct(product);
    }

    public void updateById(Product product) {
        productRepository.updateById(product);
    }


    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }

    public Optional<ProductExtDTO> queryExtById(String id) {
        return Optional.ofNullable(productDAO.getById(id)).map(DeviceDomainConvert.INSTANCE::fromRecord);
    }

    public List<ProductExtDTO> queryAllByIds(Collection<String> ids) {
        return assembleCollection(productDAO.listByIds(ids));
    }

    public List<Product> queryList(ProductCriteria criteria) {
        return productRepository.findList(criteria);
    }

    public List<ProductPO> findProductsByKeys(Collection<String> keys) {
        return productDAO.selectListByKeys(keys);
    }

    public PageInfo<ProductExtDTO> queryPage(Integer pageNum, Integer pageSize, ProductCriteria criteria) {
        PageInfo<ProductPO> pageInfo = productDAO.selectPage(pageNum, pageSize, criteria);
        return PageUtils.of(pageInfo, assembleCollection(pageInfo.getRows()));
    }

    public void upsertThingModel(String id, ProductThingModelUpsertCmd upsertCmd) {
        productRepository.findById(id).ifPresent(product -> {
            var upsert = Product.ThingModelUpsert.builder().identifier(upsertCmd.getIdentifier())
                    .abilityType(upsertCmd.getAbilityType())
                    .property(upsertCmd.getProperty())
                    .event(upsertCmd.getEvent())
                    .command(upsertCmd.getCommand())
                    .build();
            product.upsertThingModel(upsert);
            productRepository.upsertThingModel(product);
        });
    }

    /**
     * 根据标识移除某个能力
     *
     * @param id
     * @param identifier
     */
    public void removeThingModelAbility(String id, String identifier) {
        productRepository.findById(id).ifPresent(product -> {
            product.removeThingModelAbility(identifier);
            productRepository.upsertThingModel(product);
        });
    }

    /**
     * 获取产品物模型草稿
     *
     * @param productId
     * @return
     */
    public Optional<ThingModel> findThingModelDraft(String productId) {
        return productRepository.findById(productId).map(Product::getThingModelDraft);
    }

    /**
     * 物模型上线
     *
     * @param id
     */
    public void releaseThingModel(String id) {
        var product = productRepository.findById(id).orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND));
        productRepository.releaseThingModel(product);
    }

    public Optional<ThingModel> findThingModel(String productId, String version) {
        if (StrUtil.isNotBlank(version)) {
            return Optional.ofNullable(productRepository.findVesionThingModel(productId, version));
        } else {
            return productRepository.findById(productId).map(Product::getThingModelCurrent);
        }
    }

    public Optional<Product> findByKey(String key) {
        return productRepository.findByKey(key);
    }


    public Optional<ThingModel> findThingModel(String productId) {
        return Optional.ofNullable(productDAO.getById(productId)).map(product -> {
            if (StrUtil.isNotBlank(product.getThingModelVersion())) {
                return productThingModelVersionDAO.selectByProductVersion(productId, product.getThingModelVersion());
            } else {
                return null;
            }
        }).map(ProductThingModelVersion::getThingModel);
    }

    /**
     * 根据ProductKey获取物模型
     *
     * @param productKey
     * @return
     */
    public Optional<ThingModel> findThingModelByKey(String productKey) {
        return productRepository.findByKey(productKey).map(Product::getThingModelCurrent);
    }

    /**
     * 根据ProductKey获取物模型
     *
     * @param productKey
     * @param version
     * @return
     */
    public Optional<ThingModel> findThingModelByKey(String productKey, String version) {
        var product = productRepository.findByKey(productKey).orElseThrow(() -> new NotFoundException("PRODUCT_NOT_FOUND"));
        if (StrUtil.isNotBlank(version)) {
            return Optional.ofNullable(productRepository.findVesionThingModel(product.getId(), version));
        }
        {
            return Optional.ofNullable(product.getThingModelCurrent());
        }
    }

    @Cacheable(value = THING_MODEL_LATEST_PROPERTIES_NAMES, key = "'" + THING_MODEL_LATEST_PROPERTIES_KEY_PREFIX + "'+#productKey")
    public List<ThingModelProperty> findLatestThingModelPropertiesByProductKey(String productKey) {
        return findThingModelByKey(productKey).map(ThingModel::getProperties).orElse(null);
    }

    @Cacheable(value = THING_MODEL_LATEST_PROPERTY_NAMES, key = "'" + THING_MODEL_LATEST_PROPERTY_KEY_PREFIX + "'+#productKey+':'+#identifier")
    public Optional<ThingModelProperty> findLatestThingModelPropertyByProductKey(String productKey, String identifier) {
        return findLatestThingModelPropertiesByProductKey(productKey).stream().filter(property -> property.getIdentifier().equals(identifier)).findFirst();
    }


    private List<ProductExtDTO> assembleCollection(List<ProductPO> records) {
        if (CollectionUtil.isEmpty(records)) {
            return Collections.emptyList();
        }
        return DeviceDomainConvert.INSTANCE.productDtoFromRecord(records);
    }

    public void releaseProduct(String id) {
        productRepository.findById(id).ifPresent(product -> {
            productDAO.updateStatus(id, ProductStatus.RELEASE);
            List<ThingModelProperty> properties = product.getThingModelCurrent().getProperties();
            if (CollectionUtil.isNotEmpty(properties)) {
                List<TsDbColumn> columns = ProductUtils.propertiesToColumns(properties);
                tsDbTemplate.createTable(product.getKey(),columns);
            }
        });
    }

    public void revokePublish(String id) {
        productDAO.updateStatus(id, ProductStatus.DEVELOPMENT);
    }


    /**
     * 清空缓存
     *
     * @param product
     */
    @Caching(evict = {
            @CacheEvict(value = PRODUCT_NAMES, key = "'" + PRODUCT_KEY_PREFIX + "'+#product.key")
    })
    public void cleanProductCache(ProductPO product) {
    }

    @Caching(evict = {
            @CacheEvict(value = THING_MODEL_LATEST_PROPERTIES_NAMES, key = "'" + THING_MODEL_LATEST_PROPERTIES_KEY_PREFIX + "'+#product.key")
    })
    public void cleanLatestThingModelPropertiesCache(ProductPO product) {
    }

    @Caching(evict = {
            @CacheEvict(value = THING_MODEL_LATEST_PROPERTY_NAMES, key = "'" + THING_MODEL_LATEST_PROPERTY_KEY_PREFIX + "'+#productKey+':'+#identifier")
    })
    public void cleanLatestThingModelPropertyCache(String productKey, String identifier) {
    }
}
