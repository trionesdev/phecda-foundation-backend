package ms.triones.backend.core.provider.ssp.asset.impl;

import com.google.common.collect.Sets;
import ms.triones.backend.core.modules.asset.dao.entity.Asset;
import ms.triones.backend.core.modules.asset.dao.entity.SparePart;
import ms.triones.backend.core.modules.asset.service.impl.AssetService;
import ms.triones.backend.core.modules.asset.service.impl.SparePartService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class AssetProvider {

    @Lazy
    @Resource
    private AssetService assetService;

    @Lazy
    @Resource
    private SparePartService sparePartService;

    public Set<String> queryRelationDeviceNames(String assetSn) {
        Set<String> deviceNameSet = Sets.newHashSet();
        List<Asset> assets = assetService.queryAllAsset();
        assets.forEach(asset -> {
            if (CollectionUtils.isNotEmpty(asset.getDeviceNames())
                    && !StringUtils.equals(assetSn, asset.getSn())) {
                deviceNameSet.addAll(asset.getDeviceNames());
            }
        });

        List<SparePart> spareParts = sparePartService.queryAllSpareParts();
        spareParts.forEach(sparePart -> {
            if (CollectionUtils.isNotEmpty(sparePart.getDeviceNames())
                    && !StringUtils.equals(assetSn, sparePart.getSn())) {
                deviceNameSet.addAll(sparePart.getDeviceNames());
            }
        });

        return deviceNameSet;
    }

    public List<String> queryAssetRelationDeviceNames(String assetSn) {
        Optional<Asset> assetOptional = assetService.queryBySn(assetSn);
        Asset asset = assetOptional.orElse(Asset.builder().build());
        return asset.getDeviceNames();
    }
}
