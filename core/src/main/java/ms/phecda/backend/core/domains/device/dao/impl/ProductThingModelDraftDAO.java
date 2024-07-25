package ms.phecda.backend.core.domains.device.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ms.phecda.backend.core.domains.device.dao.po.ProductThingModelDraft;
import ms.phecda.backend.core.domains.device.dao.mapper.ProductThingModelDraftMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductThingModelDraftDAO extends ServiceImpl<ProductThingModelDraftMapper, ProductThingModelDraft> {

    public ProductThingModelDraft selectByProductId(String productId) {
        return baseMapper.selectOne(new LambdaQueryWrapper<ProductThingModelDraft>().eq(ProductThingModelDraft::getProductId, productId));
    }

    public void updateByProductId(ProductThingModelDraft productThingModelDraft) {
        baseMapper.update(productThingModelDraft, new LambdaQueryWrapper<ProductThingModelDraft>().eq(ProductThingModelDraft::getProductId, productThingModelDraft.getProductId()));
    }
}
