package ms.triones.backend.core.modules.device.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.moensun.commons.core.page.PageInfo;
import com.moensun.commons.core.util.PageUtils;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.device.dao.criteria.DeviceCriteria;
import ms.triones.backend.core.modules.device.dao.entity.Device;
import ms.triones.backend.core.modules.device.dao.entity.Product;
import ms.triones.backend.core.modules.device.manager.impl.DeviceManager;
import ms.triones.backend.core.modules.device.manager.impl.ProductManager;
import ms.triones.backend.core.modules.device.service.bo.DeviceExtBO;
import ms.triones.backend.core.modules.device.support.DeviceConvertMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DeviceService {
    private final DeviceManager deviceManager;
    private final ProductManager productManager;

    public void createDevice(Device device) {
        deviceManager.create(device);
    }

    public PageInfo<DeviceExtBO> queryExtPage(Integer pageNum, Integer pageSize, DeviceCriteria criteria) {
        PageInfo<Device> pageInfo = deviceManager.queryPage(pageNum, pageSize, criteria);
        if (CollectionUtil.isEmpty(pageInfo.getRows())) {
            return PageUtils.empty();
        }
        Set<String> productIds = pageInfo.getRows().stream().map(Device::getProductId).collect(Collectors.toSet());
        Map<String, Product> productMap = productManager.queryAllByIds(productIds).stream().collect(Collectors.toMap(Product::getId, v -> v, (v1, v2) -> v1));
        List<DeviceExtBO> deviceExtList = pageInfo.getRows().stream().map(t -> {
            DeviceExtBO deviceExt = DeviceConvertMapper.INSTANCE.from(t);
            deviceExt.setProduct(productMap.get(t.getProductId()));
            return deviceExt;
        }).collect(Collectors.toList());
        return PageUtils.of(pageInfo, deviceExtList);
    }


}
