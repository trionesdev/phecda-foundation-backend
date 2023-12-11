package ms.phecda.backend.core.domains.device.service.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.moensun.commons.core.page.PageInfo;
import com.moensun.commons.exception.spring.ex.BusinessException;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.manager.impl.ProductManager;
import ms.phecda.backend.core.domains.device.manager.impl.ProductThingModelDraftManager;
import ms.phecda.backend.core.domains.device.manager.impl.ProductThingModelVersionManager;
import ms.phecda.backend.core.domains.device.service.bo.ThingModelUpsertBO;
import ms.phecda.backend.core.domains.device.dao.criteria.ProductCriteria;
import ms.phecda.backend.core.domains.device.dao.entity.Product;
import ms.phecda.backend.core.domains.device.dao.entity.ProductThingModelDraft;
import ms.phecda.backend.core.domains.device.dao.entity.ProductThingModelVersion;
import ms.phecda.backend.core.domains.device.thing.model.ThingModel;
import ms.phecda.backend.core.domains.device.thing.model.ThingModelEvent;
import ms.phecda.backend.core.domains.device.thing.model.ThingModelProperty;
import ms.phecda.backend.core.domains.device.thing.model.ThingModelService;
import ms.phecda.backend.core.domains.device.thing.valuetype.ValueTypeOption;
import ms.phecda.edge.base.commons.valuetype.ValueTypeEnum;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
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

    public void createProduct(Product product) {
        productManager.create(product);
    }

    public void deleteProductById(String id) {
        productManager.deleteById(Product.builder().id(id).build());
    }

    public void updateProductById(Product product) {
        productManager.updateById(product);
    }

    public Optional<Product> queryProductById(String id) {
        return productManager.queryById(id);
    }

    public List<Product> queryList(ProductCriteria criteria) {
        return productManager.queryList(criteria);
    }

    public PageInfo<Product> queryPage(Integer pageNum, Integer pageSize, ProductCriteria criteria) {
        return productManager.queryPage(pageNum, pageSize, criteria);
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
        productThingModelDraftManager.publish(productId);
    }

    @Cacheable(value = "product", key = "#productId+':'+#version")
    public Optional<ProductThingModelVersion> queryThingModelCache(String productId, String version) {
        return queryThingModel(productId, version);
    }

    public Optional<ProductThingModelVersion> queryThingModel(String productId, String version) {
        if (StrUtil.isBlank(version)) {
            version = productManager.queryById(productId)
                    .orElseThrow(() -> new BusinessException("PRODUCT_NOT_FOUND"))
                    .getThingModelVersion();
        }
        return productThingModelVersionManager.findByProductVersion(productId, version);
    }

    public void updateProductProtocolProperties(Product product) {
        productManager.updateById(product);
    }

    public void publishProduct(String productId) {
        productManager.publish(productId);
    }

    public void revokePublishProduct(String productId) {
        productManager.revokePublish(productId);
    }
}
