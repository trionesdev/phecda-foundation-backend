package ms.phecda.backend.core.domains.device.repository.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.mybatisplus.util.MpPageUtils;
import ms.phecda.backend.core.domains.device.repository.criteria.DeviceCriteria;
import ms.phecda.backend.core.domains.device.repository.dvo.DeviceStatisticsDVO;
import ms.phecda.backend.core.domains.device.repository.po.DevicePO;
import ms.phecda.backend.core.domains.device.repository.mapper.DeviceMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class DeviceDAO extends ServiceImpl<DeviceMapper, DevicePO> {
    private LambdaQueryWrapper<DevicePO> buildQueryWrapper(DeviceCriteria criteria) {
        LambdaQueryWrapper<DevicePO> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(criteria)) {
            queryWrapper.eq(StrUtil.isNotBlank(criteria.getProductId()), DevicePO::getProductId, criteria.getProductId())
                    .eq(StrUtil.isNotBlank(criteria.getGatewayId()), DevicePO::getGatewayId, criteria.getGatewayId())
                    .in(CollectionUtils.isNotEmpty(criteria.getNames()), DevicePO::getName, criteria.getNames())
                    .in(CollectionUtils.isNotEmpty(criteria.getIds()), DevicePO::getId, criteria.getIds())
                    .exists(Objects.nonNull(criteria.getNodeType()), "select id from phecda_device_product as product where product_id=product.id and product.node_type='" + Optional.ofNullable(criteria.getNodeType()).map(Enum::name).orElse(null) + "'")
                    .exists(StrUtil.isNotBlank(criteria.getProductKey()), "select id from phecda_device_product as product where product_id=product.id and product.key='" + criteria.getProductKey() + "'")
                    .like(StrUtil.isNotBlank(criteria.getName()), DevicePO::getName, criteria.getName())
                    .like(StrUtil.isNotBlank(criteria.getRemarkName()), DevicePO::getRemarkName, criteria.getRemarkName());
        }
        return queryWrapper.orderByDesc(DevicePO::getCreatedAt);
    }

    public DeviceStatisticsDVO selectStatusStatistics() {
        return baseMapper.selectStatusStatistics();
    }

    public PageInfo<DevicePO> selectPage(Integer pageNum, Integer pageSize, DeviceCriteria criteria) {
        return MpPageUtils.of(baseMapper.selectPage(new Page<>(pageNum, pageSize), buildQueryWrapper(criteria)));
    }

    public List<DevicePO> selectList(DeviceCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria));
    }

    public Optional<DevicePO> getByName(String name) {
        LambdaQueryWrapper<DevicePO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(DevicePO::getName, name);
        return Optional.ofNullable(baseMapper.selectOne(queryWrapper));
    }

    public void removeChildDevice(String parentDeviceId, List<String> childDeviceIds) {
        if (CollectionUtils.isEmpty(childDeviceIds)) {
            return;
        }

        LambdaUpdateWrapper<DevicePO> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(DevicePO::getGatewayId, null);
        updateWrapper.eq(DevicePO::getGatewayId, parentDeviceId);
        updateWrapper.in(DevicePO::getId, childDeviceIds);

        update(updateWrapper);
    }
}
