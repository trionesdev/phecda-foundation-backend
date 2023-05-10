package ms.triones.backend.core.modules.device.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ms.triones.backend.core.modules.device.dao.entity.ProductThingModel;
import ms.triones.backend.core.modules.device.dao.mapper.ProductThingModelMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductThingModelDAO extends ServiceImpl<ProductThingModelMapper, ProductThingModel> {

    public ProductThingModel selectByProductId(String productId) {
        return baseMapper.selectOne(new LambdaQueryWrapper<ProductThingModel>().eq(ProductThingModel::getProductId, productId));
    }

    public void updateByProductId(ProductThingModel productThingModel) {
        baseMapper.update(productThingModel, new LambdaQueryWrapper<ProductThingModel>().eq(ProductThingModel::getProductId, productThingModel.getProductId()));
    }
}
