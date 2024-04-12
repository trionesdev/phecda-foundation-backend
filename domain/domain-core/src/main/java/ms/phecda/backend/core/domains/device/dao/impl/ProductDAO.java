package ms.phecda.backend.core.domains.device.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.mybatisplus.util.MpPageUtils;
import ms.phecda.backend.core.domains.device.dao.dvo.ProductStatisticsDVO;
import ms.phecda.backend.core.domains.device.dao.entity.Product;
import ms.phecda.backend.core.domains.device.dao.criteria.ProductCriteria;
import ms.phecda.backend.core.domains.device.dao.entity.enums.ProductStatusEnum;
import ms.phecda.backend.core.domains.device.dao.mapper.ProductMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class ProductDAO extends ServiceImpl<ProductMapper, Product> {

    private LambdaQueryWrapper<Product> buildQueryWrapper(ProductCriteria criteria) {
        LambdaQueryWrapper<Product> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(criteria)) {
            queryWrapper.eq(Objects.nonNull(criteria.getNodeType()), Product::getNodeType, criteria.getNodeType());
            queryWrapper.like(StringUtils.isNotBlank(criteria.getName()), Product::getName, criteria.getName());
        }
        return queryWrapper.orderByDesc(Product::getCreatedAt);
    }

    public ProductStatisticsDVO selectStatistics() {
        return baseMapper.selectStatusStatistics();
    }

    public List<Product> selectList(ProductCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria));
    }

    public PageInfo<Product> selectPage(Integer pageNum, Integer pageSize, ProductCriteria criteria) {
        return MpPageUtils.of(baseMapper.selectPage(new Page<>(pageNum, pageSize), buildQueryWrapper(criteria)));
    }

    public void updateVersion(String productId, String version) {
        baseMapper.updateById(Product.builder().id(productId).thingModelVersion(version).build());
    }

    public void updateStatus(String id, ProductStatusEnum status) {
        LambdaUpdateWrapper<Product> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(Product::getStatus, status);
        updateWrapper.eq(Product::getId, id);
        update(updateWrapper);
    }

    public Product selectByKey(String key) {
        return baseMapper.selectOne(Wrappers.<Product>lambdaQuery().eq(Product::getKey, key).last("limit 1"));
    }
}
