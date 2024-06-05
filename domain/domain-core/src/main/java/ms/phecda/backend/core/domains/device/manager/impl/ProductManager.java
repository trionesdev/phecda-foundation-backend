package ms.phecda.backend.core.domains.device.manager.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.core.util.PageUtils;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.repository.criteria.ProductCriteria;
import ms.phecda.backend.core.domains.device.repository.dvo.ProductStatisticsDVO;
import ms.phecda.backend.core.domains.device.repository.po.ProductPO;
import ms.phecda.backend.core.domains.device.repository.po.ProductThingModelVersion;
import ms.phecda.backend.core.domains.device.repository.po.enums.NodeTypeEnum;
import ms.phecda.backend.core.domains.device.repository.po.enums.ProductStatusEnum;
import ms.phecda.backend.core.domains.device.repository.impl.ProductDAO;
import ms.phecda.backend.core.domains.device.repository.impl.ProductThingModelVersionDAO;
import ms.phecda.backend.core.domains.device.manager.dto.ProductDTO;
import ms.phecda.backend.core.domains.device.internal.DeviceConvert;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModel;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelProperty;
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
    private final ProductDAO productDAO;
    private final ProductThingModelVersionDAO productThingModelVersionDAO;

    public ProductStatisticsDVO queryStatistics() {
        return productDAO.selectStatistics();
    }

    public void create(ProductPO product) {
        if (Objects.equals(NodeTypeEnum.GATEWAY, product.getNodeType())) {
            product.setType(ProductPO.Type.GATEWAY);
        }
        productDAO.save(product);
    }

    public void deleteById(ProductPO product) {
        productDAO.removeById(product);
    }

    public void updateById(ProductPO product) {
        productDAO.updateById(product);
    }


    public Optional<ProductPO> queryById(String id) {
        return Optional.ofNullable(productDAO.getById(id));
    }

    public Optional<ProductDTO> queryExtById(String id) {
        return Optional.ofNullable(productDAO.getById(id)).map(DeviceConvert.INSTANCE::fromRecord);
    }

    public List<ProductDTO> queryAllByIds(Collection<String> ids) {
        return assembleCollection(productDAO.listByIds(ids));
    }

    public List<ProductDTO> queryList(ProductCriteria criteria) {
        return assembleCollection(productDAO.selectList(criteria));
    }

    public PageInfo<ProductDTO> queryPage(Integer pageNum, Integer pageSize, ProductCriteria criteria) {
        PageInfo<ProductPO> pageInfo = productDAO.selectPage(pageNum, pageSize, criteria);
        return PageUtils.of(pageInfo, assembleCollection(pageInfo.getRows()));
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


    private List<ProductDTO> assembleCollection(List<ProductPO> records) {
        if (CollectionUtil.isEmpty(records)) {
            return Collections.emptyList();
        }
        return DeviceConvert.INSTANCE.productDtoFromRecord(records);
    }

    public void publish(String id) {
        productDAO.updateStatus(id, ProductStatusEnum.RELEASE);
    }

    public void revokePublish(String id) {
        productDAO.updateStatus(id, ProductStatusEnum.DEVELOPMENT);
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
