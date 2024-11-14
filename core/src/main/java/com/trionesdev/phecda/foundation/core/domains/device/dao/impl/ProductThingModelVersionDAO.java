package com.trionesdev.phecda.foundation.core.domains.device.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.ProductThingModelVersion;
import com.trionesdev.phecda.foundation.core.domains.device.dao.mapper.ProductThingModelVersionMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductThingModelVersionDAO extends ServiceImpl<ProductThingModelVersionMapper, ProductThingModelVersion> {

    public ProductThingModelVersion selectByProductVersion(String productId, String version) {
        return lambdaQuery().eq(ProductThingModelVersion::getId, version).eq(ProductThingModelVersion::getProductId, productId).one();
    }

    public void deleteByProductId(String productId) {
        lambdaUpdate().eq(ProductThingModelVersion::getProductId, productId).remove();
    }

}
