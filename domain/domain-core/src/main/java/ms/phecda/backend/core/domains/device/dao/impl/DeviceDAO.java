package ms.phecda.backend.core.domains.device.dao.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.mybatisplus.util.MpPageUtils;
import ms.phecda.backend.core.domains.device.dao.criteria.DeviceCriteria;
import ms.phecda.backend.core.domains.device.dao.dvo.DeviceStatisticsDVO;
import ms.phecda.backend.core.domains.device.dao.entity.Device;
import ms.phecda.backend.core.domains.device.dao.mapper.DeviceMapper;
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
            queryWrapper.eq(StrUtil.isNotBlank(criteria.getProductId()), Device::getProductId, criteria.getProductId())
                    .eq(StrUtil.isNotBlank(criteria.getGatewayId()), Device::getGatewayId, criteria.getGatewayId())
                    .in(CollectionUtils.isNotEmpty(criteria.getNames()), Device::getName, criteria.getNames())
                    .in(CollectionUtils.isNotEmpty(criteria.getIds()), Device::getId, criteria.getIds())
                    .exists(Objects.nonNull(criteria.getNodeType()), "select id from phecda_device_product as product where product_id=product.id and product.node_type='" + Optional.ofNullable(criteria.getNodeType()).map(Enum::name).orElse(null) + "'")
                    .exists(StrUtil.isNotBlank(criteria.getProductKey()), "select id from phecda_device_product as product where product_id=product.id and product.key='" + criteria.getProductKey() + "'")
                    .like(StrUtil.isNotBlank(criteria.getName()), Device::getName, criteria.getName())
                    .like(StrUtil.isNotBlank(criteria.getRemarkName()), Device::getRemarkName, criteria.getRemarkName());
        }
        return queryWrapper.orderByDesc(Device::getCreatedAt);
    }

    public DeviceStatisticsDVO selectStatusStatistics() {
        return baseMapper.selectStatusStatistics();
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
        updateWrapper.set(Device::getGatewayId, null);
        updateWrapper.eq(Device::getGatewayId, parentDeviceId);
        updateWrapper.in(Device::getId, childDeviceIds);

        update(updateWrapper);
    }
}
