package ms.triones.backend.core.modules.device.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moensun.commons.core.page.PageInfo;
import com.moensun.commons.mybatisplus.util.MpPageUtils;
import ms.triones.backend.core.modules.device.dao.criteria.ProductCriteria;
import ms.triones.backend.core.modules.device.dao.entity.Product;
import ms.triones.backend.core.modules.device.dao.mapper.ProductMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class ProductDAO extends ServiceImpl<ProductMapper, Product> {

    private LambdaQueryWrapper<Product> buildQueryWrapper(ProductCriteria criteria) {
        LambdaQueryWrapper<Product> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(criteria)) {
            queryWrapper.eq(Objects.nonNull(criteria.getNodeType()), Product::getNodeType, criteria.getNodeType());
        }
        return queryWrapper;
    }

    public List<Product> selectList(ProductCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria));
    }

    public PageInfo<Product> selectPage(Integer pageNum, Integer pageSize, ProductCriteria criteria) {
        LambdaQueryWrapper<Product> queryWrapper = buildQueryWrapper(criteria);
        queryWrapper.orderByDesc(Product::getUpdatedAt);
        return MpPageUtils.of(baseMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }

    public void updateVersion(String productId, String version) {
        baseMapper.updateById(Product.builder().id(productId).thingModelVersion(version).build());
    }
}
