package ms.phecda.backend.core.domains.device.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ms.phecda.backend.core.domains.device.repository.po.ProductThingModelVersion;
import ms.phecda.backend.core.domains.device.repository.mapper.ProductThingModelVersionMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductThingModelVersionDAO extends ServiceImpl<ProductThingModelVersionMapper, ProductThingModelVersion> {

    public ProductThingModelVersion selectByProductVersion(String productId, String version) {
        return lambdaQuery().eq(ProductThingModelVersion::getId, version).eq(ProductThingModelVersion::getProductId, productId).one();
    }

}
