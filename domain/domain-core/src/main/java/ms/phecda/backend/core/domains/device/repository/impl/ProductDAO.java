package ms.phecda.backend.core.domains.device.repository.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.mybatisplus.util.MpPageUtils;
import ms.phecda.backend.core.domains.device.repository.dvo.ProductStatisticsDVO;
import ms.phecda.backend.core.domains.device.repository.po.ProductPO;
import ms.phecda.backend.core.domains.device.repository.criteria.ProductCriteria;
import ms.phecda.backend.core.domains.device.repository.po.enums.ProductStatusEnum;
import ms.phecda.backend.core.domains.device.repository.mapper.ProductMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductDAO extends ServiceImpl<ProductMapper, ProductPO> {

    private LambdaQueryWrapper<ProductPO> buildQueryWrapper(ProductCriteria criteria) {
        LambdaQueryWrapper<ProductPO> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(criteria)) {
            queryWrapper.eq(Objects.nonNull(criteria.getNodeType()), ProductPO::getNodeType, criteria.getNodeType());
            queryWrapper.like(StringUtils.isNotBlank(criteria.getName()), ProductPO::getName, criteria.getName());
        }
        return queryWrapper.orderByDesc(ProductPO::getCreatedAt);
    }

    public ProductStatisticsDVO selectStatistics() {
        return baseMapper.selectStatusStatistics();
    }

    public List<ProductPO> selectList(ProductCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria));
    }

    public PageInfo<ProductPO> selectPage(Integer pageNum, Integer pageSize, ProductCriteria criteria) {
        return MpPageUtils.of(baseMapper.selectPage(new Page<>(pageNum, pageSize), buildQueryWrapper(criteria)));
    }

    public void updateVersion(String productId, String version) {
        baseMapper.updateById(ProductPO.builder().id(productId).thingModelVersion(version).build());
    }

    public void updateStatus(String id, ProductStatusEnum status) {
        LambdaUpdateWrapper<ProductPO> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(ProductPO::getStatus, status);
        updateWrapper.eq(ProductPO::getId, id);
        update(updateWrapper);
    }

    public ProductPO selectByKey(String key) {
        return baseMapper.selectOne(Wrappers.<ProductPO>lambdaQuery().eq(ProductPO::getKey, key).last("limit 1"));
    }

    public List<ProductPO> selectListByKeys(Collection<String> keys) {
        if (CollectionUtil.isEmpty(keys)) {
            return Collections.emptyList();
        }
        return baseMapper.selectList(Wrappers.<ProductPO>lambdaQuery().in(ProductPO::getKey, keys));
    }

}
