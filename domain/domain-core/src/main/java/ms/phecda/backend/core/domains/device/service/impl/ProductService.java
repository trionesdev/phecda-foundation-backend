package ms.phecda.backend.core.domains.device.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.exception.BusinessException;
import com.trionesdev.commons.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.internal.DeviceBeanConvert;
import ms.phecda.backend.core.domains.device.repository.criteria.ProductCriteria;
import ms.phecda.backend.core.domains.device.repository.dvo.ProductStatisticsDVO;
import ms.phecda.backend.core.domains.device.repository.po.ProductPO;
import ms.phecda.backend.core.domains.device.repository.po.ProductThingModelDraft;
import ms.phecda.backend.core.domains.device.repository.po.ProductThingModelVersion;
import ms.phecda.backend.core.domains.device.manager.dto.ProductExtDTO;
import ms.phecda.backend.core.domains.device.manager.impl.ProductManager;
import ms.phecda.backend.core.domains.device.manager.impl.ProductThingModelDraftManager;
import ms.phecda.backend.core.domains.device.manager.impl.ProductThingModelVersionManager;
import ms.phecda.backend.core.domains.device.service.bo.ThingModelUpsertBO;
import ms.phecda.backend.core.domains.device.internal.util.DeviceUtils;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModel;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelEvent;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelProperty;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelService;
import ms.phecda.backend.core.domains.device.internal.model.thing.valuetype.ValueTypeEnum;
import ms.phecda.backend.core.domains.device.internal.model.thing.valuetype.ValueTypeOption;
import ms.phecda.backend.core.dto.dervice.ProductDTO;
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
    private final DeviceBeanConvert convert;
    private final ProductManager productManager;
    private final ProductThingModelDraftManager productThingModelDraftManager;
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

    public void createProduct(ProductPO product) {
        if (StrUtil.isBlank(product.getKey())) {
            product.setKey(generateProductKey());
        }
        productManager.create(product);
    }


    public void deleteProductById(String id) {
        ProductPO productSnap = productManager.queryById(id).orElseThrow(() -> new BusinessException("产品不存在"));
        productManager.deleteById(productSnap);
        productManager.cleanProductCache(productSnap);
    }

    @Transactional
    public void updateProductById(ProductPO product) {
        ProductPO productSnap = productManager.queryById(product.getId()).orElseThrow(() -> new BusinessException("产品不存在"));
        productManager.updateById(product);
        productManager.cleanProductCache(productSnap); //清空缓存
    }


    public Optional<ProductDTO> queryProductById(String id) {
        return productManager.queryById(id).map(this::assemble);
    }


    public Optional<ProductPO> findProductByKey(String key) {
        return productManager.findByKey(key);
    }

    public List<ProductExtDTO> queryList(ProductCriteria criteria) {
        return productManager.queryList(criteria);
    }

    public PageInfo<ProductExtDTO> queryPage(Integer pageNum, Integer pageSize, ProductCriteria criteria) {
        return productManager.queryPage(pageNum, pageSize, criteria);
    }

    public List<ProductDTO> findProductsByKeys(Collection<String> keys) {
        return assembleProducts(productManager.findProductsByKeys(keys));
    }


    public Optional<ProductThingModelDraft> findProductThingModelDraft(String productId) {
        return productThingModelDraftManager.queryByProductId(productId);
    }

    public void upsertThingModel(String productId, ThingModelUpsertBO thingModelUpsert) {
        ProductThingModelDraft ptmSnap = productThingModelDraftManager.queryByProductId(productId)
                .orElse(ProductThingModelDraft.builder()
                        .productId(productId)
                        .thingModel(new ThingModel())
                        .build());
        if (StrUtil.isBlank(thingModelUpsert.getIdentifier())) {
            if (Objects.nonNull(thingModelUpsert.getProperty()) &&
                    ptmSnap.getThingModel()
                            .getProperties()
                            .stream()
                            .anyMatch(t -> Objects.equals(thingModelUpsert.getProperty()
                                    .getIdentifier(), t.getIdentifier()))) {
                throw new BusinessException("ABILITY_IDENTIFIER_DUPLICATED");
            }
            if (Objects.nonNull(thingModelUpsert.getService()) &&
                    ptmSnap.getThingModel()
                            .getServices()
                            .stream()
                            .anyMatch(t -> Objects.equals(thingModelUpsert.getService()
                                    .getIdentifier(), t.getIdentifier()))) {
                throw new BusinessException("ABILITY_IDENTIFIER_DUPLICATED");
            }
            if (Objects.nonNull(thingModelUpsert.getEvent()) &&
                    ptmSnap.getThingModel()
                            .getEvents()
                            .stream()
                            .anyMatch(t -> Objects.equals(thingModelUpsert.getEvent()
                                    .getIdentifier(), t.getIdentifier()))) {
                throw new BusinessException("ABILITY_IDENTIFIER_DUPLICATED");
            }
            switch (thingModelUpsert.getAbilityType()) {
                case PROPERTY:
                    ptmSnap.getThingModel().getProperties().add(thingModelUpsert.getProperty());
                    break;
                case SERVICE:
                    ptmSnap.getThingModel().getServices().add(thingModelUpsert.getService());
                    break;
                case EVENT:
                    ptmSnap.getThingModel().getEvents().add(thingModelUpsert.getEvent());
                default:
                    break;
            }
        } else {
            switch (thingModelUpsert.getAbilityType()) {
                case PROPERTY:
                    ThingModelProperty tmp = thingModelUpsert.getProperty();
                    if (Objects.nonNull(tmp)) {
                        List<ThingModelProperty> properties = ptmSnap.getThingModel().getProperties().stream().map(t -> {
                            if (Objects.equals(t.getIdentifier(), thingModelUpsert.getIdentifier())) {
                                return tmp;
                            } else {
                                return t;
                            }
                        }).collect(Collectors.toList());
                        ptmSnap.getThingModel().setProperties(properties);
                    }
                    break;
                case SERVICE:
                    ThingModelService tms = thingModelUpsert.getService();
                    if (Objects.nonNull(tms)) {
                        List<ThingModelService> services = ptmSnap.getThingModel().getServices().stream().map(t -> {
                            if (Objects.equals(t.getIdentifier(), thingModelUpsert.getIdentifier())) {
                                return tms;
                            } else {
                                return t;
                            }
                        }).collect(Collectors.toList());
                        ptmSnap.getThingModel().setServices(services);
                    }
                    break;
                case EVENT:
                    ThingModelEvent tme = thingModelUpsert.getEvent();
                    if (Objects.nonNull(tme)) {
                        List<ThingModelEvent> events = ptmSnap.getThingModel().getEvents().stream().map(t -> {
                            if (Objects.equals(t.getIdentifier(), thingModelUpsert.getIdentifier())) {
                                return tme;
                            } else {
                                return t;
                            }
                        }).collect(Collectors.toList());
                        ptmSnap.getThingModel().setEvents(events);
                    }
                    break;
            }
        }
        productThingModelDraftManager.upsertByProductId(ptmSnap);
    }

    public void deleteThingModel(String productId, String identifier) {
        productThingModelDraftManager.queryByProductId(productId).ifPresent(t -> {
            t.getThingModel().getProperties().removeIf((property) -> Objects.equals(identifier, property.getIdentifier()));
            t.getThingModel().getServices().removeIf((property) -> Objects.equals(identifier, property.getIdentifier()));
            t.getThingModel().getEvents().removeIf((property) -> Objects.equals(identifier, property.getIdentifier()));
            productThingModelDraftManager.upsertByProductId(t);
        });
    }

    /**
     * 发布物模型
     *
     * @param productId
     */
    public void publishThingModel(String productId) {
        ProductPO product = productManager.queryById(productId).orElseThrow(() -> new NotFoundException("PRODUCT_NOT_FOUND"));
        productThingModelDraftManager.publish(productId);
        productManager.cleanLatestThingModelPropertiesCache(product);//清空缓存

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

    public Optional<ProductThingModelVersion> queryThingModel(String productId, String version) {
        if (StrUtil.isBlank(version)) {
            version = productManager.queryById(productId)
                    .orElseThrow(() -> new NotFoundException("PRODUCT_NOT_FOUND"))
                    .getThingModelVersion();
        }
        return productThingModelVersionManager.findByProductVersion(productId, version);
    }

    public Optional<ProductThingModelVersion> queryThingModelByKey(String key, String version) {
        ProductPO product = productManager.findByKey(key).orElseThrow(() -> new NotFoundException("PRODUCT_NOT_FOUND"));
        if (StrUtil.isBlank(version)) {
            version = productManager.queryById(product.getId())
                    .orElseThrow(() -> new NotFoundException("PRODUCT_NOT_FOUND"))
                    .getThingModelVersion();
        }
        return productThingModelVersionManager.findByProductVersion(product.getId(), version);
    }


    public void updateProductProtocolProperties(ProductPO product) {
        productManager.updateById(product);
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

    private ProductDTO assemble(ProductPO product) {
        if (Objects.isNull(product)) {
            return null;
        }
        return convert.poToDto(product);
    }

    private List<ProductDTO> assembleProducts(List<ProductPO> records) {
        if (CollectionUtil.isEmpty(records)) {
            return Collections.emptyList();
        }
        return records.stream().map((product) -> {
            return convert.poToDto(product);
        }).collect(Collectors.toList());
    }

}
