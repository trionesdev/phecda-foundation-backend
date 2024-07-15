package ms.phecda.backend.core.domains.device.manager.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.core.util.PageUtils;
import com.trionesdev.commons.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.dto.ProductDTO;
import ms.phecda.backend.core.domains.device.dto.ProductThingModelUpsertCmd;
import ms.phecda.backend.core.domains.device.internal.DeviceBeanConvert;
import ms.phecda.backend.core.domains.device.dao.criteria.ProductCriteria;
import ms.phecda.backend.core.domains.device.dao.dvo.ProductStatisticsDVO;
import ms.phecda.backend.core.domains.device.dao.po.ProductPO;
import ms.phecda.backend.core.domains.device.dao.po.ProductThingModelVersion;
import ms.phecda.backend.core.domains.device.internal.entity.Product;
import ms.phecda.backend.core.domains.device.internal.enums.NodeType;
import ms.phecda.backend.core.domains.device.internal.enums.ProductStatus;
import ms.phecda.backend.core.domains.device.dao.impl.ProductDAO;
import ms.phecda.backend.core.domains.device.dao.impl.ProductThingModelVersionDAO;
import ms.phecda.backend.core.domains.device.internal.enums.ProductType;
import ms.phecda.backend.core.domains.device.manager.dto.ProductExtDTO;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModel;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelProperty;
import ms.phecda.backend.core.domains.device.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ms.phecda.backend.core.domains.device.internal.DeviceCacheConstants.*;

@RequiredArgsConstructor
@Service
public class ProductManager {
    private final DeviceBeanConvert convert;
    private final ProductDAO productDAO;
    private final ProductThingModelVersionDAO productThingModelVersionDAO;
    private final ProductRepository productRepository;

    public ProductStatisticsDVO queryStatistics() {
        return productDAO.selectStatistics();
    }

    public void create(ProductPO product) {
        if (Objects.equals(NodeType.GATEWAY, product.getNodeType())) {
            product.setType(ProductType.GATEWAY);
        }
        productDAO.save(product);
    }

    public void deleteById(ProductPO product) {
        productDAO.removeById(product);
    }

    public void updateById(ProductPO product) {
        productDAO.updateById(product);
    }


    public Optional<ProductDTO> queryById(String id) {
        return productRepository.findById(id).map(convert::productEntityToDto);
    }

    public Optional<ProductExtDTO> queryExtById(String id) {
        return Optional.ofNullable(productDAO.getById(id)).map(DeviceBeanConvert.INSTANCE::fromRecord);
    }

    public List<ProductExtDTO> queryAllByIds(Collection<String> ids) {
        return assembleCollection(productDAO.listByIds(ids));
    }

    public List<ProductExtDTO> queryList(ProductCriteria criteria) {
        return assembleCollection(productDAO.selectList(criteria));
    }

    public List<ProductPO> findProductsByKeys(Collection<String> keys) {
        return productDAO.selectListByKeys(keys);
    }

    public PageInfo<ProductExtDTO> queryPage(Integer pageNum, Integer pageSize, ProductCriteria criteria) {
        PageInfo<ProductPO> pageInfo = productDAO.selectPage(pageNum, pageSize, criteria);
        return PageUtils.of(pageInfo, assembleCollection(pageInfo.getRows()));
    }

    public void upsertThingModel(String id, ProductThingModelUpsertCmd upsertCmd){
        productRepository.findById(id).ifPresent(product -> {
            var upsert = Product.ThingModelUpsert.builder().build();
            product.upsertThingModel(upsert);
            productRepository.upsertThingModel(product);
        });
    }

    public void removeThingModelAbility(String id,String identifier){
        productRepository.findById(id).ifPresent(product -> {
            product.removeThingModelAbility(identifier);
            productRepository.upsertThingModel(product);
        });
    }

    public void releaseThingModel(String id){
       var product = productRepository.findById(id).orElseThrow(()->new NotFoundException("PRODUCT_NOT_FOUND"));
       productRepository.releaseThingModel(product);
    }

    public Optional<ThingModel> findThingModel(String productId,String version) {
        if (StrUtil.isNotBlank(version)){
            return Optional.ofNullable(productRepository.findVesionThingModel(productId,version));
        }else {
            return productRepository.findById(productId).map(Product::getThingModelCurrent);
        }
    }

    @Cacheable(value = {PRODUCT_NAMES}, key = "'" + PRODUCT_KEY_PREFIX + "'+#key", unless = "#result==null")
    public Optional<ProductPO> findByKey(String key) {
        return Optional.ofNullable(productDAO.selectByKey(key));
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

    public Optional<ThingModel> findThingModelByKey(String productKey) {
        return Optional.ofNullable(productDAO.selectByKey(productKey)).map(product -> {
            if (StrUtil.isNotBlank(product.getThingModelVersion())) {
                return productThingModelVersionDAO.selectByProductVersion(product.getId(), product.getThingModelVersion());
            } else {
                return null;
            }
        }).map(ProductThingModelVersion::getThingModel);
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
        return DeviceBeanConvert.INSTANCE.productDtoFromRecord(records);
    }

    public void publish(String id) {
        productDAO.updateStatus(id, ProductStatus.RELEASE);
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
