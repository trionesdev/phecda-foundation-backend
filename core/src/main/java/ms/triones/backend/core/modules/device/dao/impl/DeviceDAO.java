package ms.triones.backend.core.modules.device.dao.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moensun.commons.core.page.PageInfo;
import com.moensun.commons.mybatisplus.util.MpPageUtils;
import ms.triones.backend.core.modules.device.dao.criteria.DeviceCriteria;
import ms.triones.backend.core.modules.device.dao.entity.Device;
import ms.triones.backend.core.modules.device.dao.mapper.DeviceMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class DeviceDAO extends ServiceImpl<DeviceMapper, Device> {
    private LambdaQueryWrapper<Device> buildQueryWrapper(DeviceCriteria criteria) {
        LambdaQueryWrapper<Device> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(criteria)) {
            queryWrapper.eq(StrUtil.isNotBlank(criteria.getProductId()), Device::getProductId, criteria.getProductId());
            queryWrapper.in(CollectionUtils.isNotEmpty(criteria.getNames()), Device::getName, criteria.getNames());
        }
        return queryWrapper.orderByDesc(Device::getCreatedAt);
    }

    public PageInfo<Device> selectPage(Integer pageNum, Integer pageSize, DeviceCriteria criteria) {
        return MpPageUtils.of(baseMapper.selectPage(new Page<>(pageNum, pageSize), buildQueryWrapper(criteria)));
    }

    public List<Device> selectList(DeviceCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria));
    }
}
