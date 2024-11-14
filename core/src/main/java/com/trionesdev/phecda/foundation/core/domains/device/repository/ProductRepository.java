package com.trionesdev.phecda.foundation.core.domains.device.repository;

import cn.hutool.core.util.StrUtil;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.ProductCriteria;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.device.dao.impl.ProductDAO;
import com.trionesdev.phecda.foundation.core.domains.device.dao.impl.ProductThingModelVersionDAO;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.ProductPO;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.ProductThingModelVersion;
import com.trionesdev.phecda.foundation.core.domains.device.internal.DeviceDomainConvert;
import com.trionesdev.phecda.foundation.core.domains.device.internal.aggregate.entity.Product;
import com.trionesdev.phecda.model.device.thing.ThingModel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class ProductRepository {
    private final DeviceDomainConvert convert;
    private final ProductDAO productDAO;
    private final ProductThingModelVersionDAO thingModelVersionDAO;

    public void save(Product product) {
        productDAO.save(convert.productEntityToPo(product));
    }

    @Transactional
    public void deleteProduct(Product product) {
        productDAO.removeById(product.getId());
        thingModelVersionDAO.deleteByProductId(product.getId());
    }

    public void updateById(Product product) {
        productDAO.updateById(convert.productEntityToPo(product));
    }

    public Optional<Product> findById(String id) {
        return Optional.ofNullable(productDAO.getById(id)).map(t -> {
            var product = convert.productPoToEntity(t);
            if (StrUtil.isNotBlank(product.getThingModelVersion())) {
                Optional.ofNullable(thingModelVersionDAO.selectByProductVersion(product.getId(), product.getThingModelVersion())).map(ProductThingModelVersion::getThingModel).ifPresent(product::setThingModelCurrent);
            }
            return product;
        });
    }

    public Optional<Product> findByKey(String key) {
        return Optional.ofNullable(productDAO.selectByKey(key)).map(t -> {
            var product = convert.productPoToEntity(t);
            if (StrUtil.isNotBlank(product.getThingModelVersion())) {
                Optional.ofNullable(thingModelVersionDAO.selectByProductVersion(product.getId(), product.getThingModelVersion())).map(ProductThingModelVersion::getThingModel).ifPresent(product::setThingModelCurrent);
            }
            return product;
        });
    }


    public void upsertThingModel(Product product) {
        var productPO = ProductPO.builder().id(product.getId()).thingModelDraft(product.getThingModelDraft()).build();
        productDAO.updateById(productPO);
    }

    @Transactional
    public void releaseThingModel(Product product) {
        var version = ProductThingModelVersion.builder().productId(product.getId()).thingModel(product.getThingModelDraft()).build();
        thingModelVersionDAO.save(version);
        productDAO.updateVersion(product.getId(), version.getId());
    }

    public ThingModel findVesionThingModel(String productId, String version) {
        return Optional.ofNullable(thingModelVersionDAO.selectByProductVersion(productId, version)).map(ProductThingModelVersion::getThingModel).orElse(null);
    }

    private List<Product> assembleProducts(List<ProductPO> products) {
        if (CollectionUtils.isEmpty(products)) {
            return new ArrayList<>();
        }
        return products.stream().map(productPO -> {
            return convert.productPoToEntity(productPO);
        }).collect(Collectors.toList());
    }

    public List<Product> findList(ProductCriteria criteria) {
        return assembleProducts(productDAO.selectList(criteria));
    }
}
