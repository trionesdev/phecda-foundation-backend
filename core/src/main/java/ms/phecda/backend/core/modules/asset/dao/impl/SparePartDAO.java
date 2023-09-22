package ms.phecda.backend.core.modules.asset.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moensun.commons.core.page.PageInfo;
import com.moensun.commons.mybatisplus.util.MpPageUtils;
import ms.phecda.backend.core.modules.asset.dao.criteria.SparePartCriteria;
import ms.phecda.backend.core.modules.asset.dao.entity.SparePart;
import ms.phecda.backend.core.modules.asset.dao.mapper.SparePartMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 资产配件 服务实现类
 * </p>
 *
 * @author jscoe
 * @since 2023-06-30
 */

@Repository
public class SparePartDAO extends ServiceImpl<SparePartMapper, SparePart> {
    private LambdaQueryWrapper<SparePart> buildQueryWrapper(SparePartCriteria criteria) {
        LambdaQueryWrapper<SparePart> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(criteria)) {
            queryWrapper.eq(Objects.nonNull(criteria.getAssetSn()), SparePart::getAssetSn, criteria.getAssetSn())
                    .in(CollectionUtils.isNotEmpty(criteria.getSns()), SparePart::getSn, criteria.getSns());
            queryWrapper.orderByDesc(SparePart::getCreatedAt);
        }
        return queryWrapper;
    }

    public List<SparePart> selectList(SparePartCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria));
    }

    public PageInfo<SparePart> selectPage(Integer pageNum, Integer pageSize, SparePartCriteria criteria) {
        return MpPageUtils.of(baseMapper.selectPage(new Page<>(pageNum, pageSize), buildQueryWrapper(criteria)));
    }

    public Optional<SparePart> queryByDeviceName(String deviceName) {
        LambdaQueryWrapper<SparePart> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.apply("jsonb_exists(device_names, {0})", deviceName);
        return Optional.ofNullable(getOne(queryWrapper));
    }
}
