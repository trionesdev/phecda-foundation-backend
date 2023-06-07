package ms.triones.backend.core.modules.device.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ms.triones.backend.core.modules.device.dao.entity.ProductThingModelVersion;
import ms.triones.backend.core.modules.device.dao.mapper.ProductThingModelVersionMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductThingModelVersionDAO extends ServiceImpl<ProductThingModelVersionMapper, ProductThingModelVersion> {

    public ProductThingModelVersion selectByProductVersion(String productId, String version) {
        return baseMapper.selectOne(new LambdaQueryWrapper<ProductThingModelVersion>().eq(ProductThingModelVersion::getId, version).eq(ProductThingModelVersion::getProductId, productId));
    }

}
