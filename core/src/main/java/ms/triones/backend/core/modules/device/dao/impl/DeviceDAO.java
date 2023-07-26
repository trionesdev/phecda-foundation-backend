package ms.triones.backend.core.modules.device.dao.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
import java.util.Optional;

@Repository
public class DeviceDAO extends ServiceImpl<DeviceMapper, Device> {
    private LambdaQueryWrapper<Device> buildQueryWrapper(DeviceCriteria criteria) {
        LambdaQueryWrapper<Device> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(criteria)) {
            queryWrapper.eq(StrUtil.isNotBlank(criteria.getProductId()), Device::getProductId, criteria.getProductId());
            queryWrapper.eq(StrUtil.isNotBlank(criteria.getGatewayDeviceId()), Device::getGatewayDeviceId, criteria.getGatewayDeviceId());
            queryWrapper.in(CollectionUtils.isNotEmpty(criteria.getNames()), Device::getName, criteria.getNames());
            queryWrapper.in(CollectionUtils.isNotEmpty(criteria.getIds()), Device::getId, criteria.getIds());
            queryWrapper.like(StrUtil.isNotBlank(criteria.getName()), Device::getName, criteria.getName());
            queryWrapper.like(StrUtil.isNotBlank(criteria.getRemarkName()), Device::getRemarkName, criteria.getRemarkName());
        }
        return queryWrapper.orderByDesc(Device::getCreatedAt);
    }

    public PageInfo<Device> selectPage(Integer pageNum, Integer pageSize, DeviceCriteria criteria) {
        return MpPageUtils.of(baseMapper.selectPage(new Page<>(pageNum, pageSize), buildQueryWrapper(criteria)));
    }

    public List<Device> selectList(DeviceCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria));
    }

    public Optional<Device> getByName(String name) {
        LambdaQueryWrapper<Device> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Device::getName, name);
        return Optional.ofNullable(baseMapper.selectOne(queryWrapper));
    }

    public void removeChildDevice(String parentDeviceId, List<String> childDeviceIds) {
        if (CollectionUtils.isEmpty(childDeviceIds)) {
            return;
        }

        LambdaUpdateWrapper<Device> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(Device::getGatewayDeviceId, null);
        updateWrapper.eq(Device::getGatewayDeviceId, parentDeviceId);
        updateWrapper.in(Device::getId, childDeviceIds);

        update(updateWrapper);
    }
}
