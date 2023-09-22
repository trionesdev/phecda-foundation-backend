package ms.phecda.backend.core.modules.asset.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moensun.commons.core.page.PageInfo;
import com.moensun.commons.mybatisplus.util.MpPageUtils;
import ms.phecda.backend.core.modules.asset.dao.entity.Asset;
import ms.phecda.backend.core.modules.asset.dao.criteria.AssetCriteria;
import ms.phecda.backend.core.modules.asset.dao.mapper.AssetMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 资产(生产设备) 服务实现类
 * </p>
 *
 * @author jscoe
 * @since 2023-06-30
 */

@Repository
public class AssetDAO extends ServiceImpl<AssetMapper, Asset> {

    private LambdaQueryWrapper<Asset> buildQueryWrapper(AssetCriteria criteria) {
        LambdaQueryWrapper<Asset> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(criteria)) {
            queryWrapper.eq(StringUtils.isNotBlank(criteria.getTypeCode()), Asset::getTypeCode, criteria.getTypeCode())
                    .eq(StringUtils.isNotBlank(criteria.getLocationCode()), Asset::getLocationCode, criteria.getLocationCode())
                    .in(CollectionUtils.isNotEmpty(criteria.getSns()), Asset::getSn, criteria.getSns())
                    .eq(ObjectUtils.isNotEmpty(criteria.getState()), Asset::getState, criteria.getState());
        }
        return queryWrapper.orderByDesc(Asset::getCreatedAt);
    }

    public PageInfo<Asset> selectPage(Integer pageNum, Integer pageSize, AssetCriteria criteria) {
        return MpPageUtils.of(baseMapper.selectPage(new Page<>(pageNum, pageSize), buildQueryWrapper(criteria)));
    }

    public List<Asset> selectList(AssetCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria));
    }

    public Optional<Asset> getBySn(String sn) {
        LambdaQueryWrapper<Asset> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Asset::getSn, sn);
        return Optional.ofNullable(baseMapper.selectOne(queryWrapper));
    }

    public Optional<Asset> queryByDeviceName(String deviceName) {
        LambdaQueryWrapper<Asset> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.apply("jsonb_exists(device_names, {0})", deviceName);
        return Optional.ofNullable(getOne(queryWrapper));
    }
}
