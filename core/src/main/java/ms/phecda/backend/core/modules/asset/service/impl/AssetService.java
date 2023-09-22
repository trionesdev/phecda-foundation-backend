package ms.phecda.backend.core.modules.asset.service.impl;

import com.moensun.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.modules.asset.dao.criteria.AssetCriteria;
import ms.phecda.backend.core.modules.asset.dao.entity.Asset;
import ms.phecda.backend.core.modules.asset.manager.AssetManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 资产(生产设备) 服务类
 * </p>
 *
 * @author jscoe
 * @since 2023-06-30
 */

@RequiredArgsConstructor
@Service
public class AssetService {

    private final AssetManager assetManager;

    public void create(Asset entity) {
        assetManager.create(entity);
    }

    public void deleteById(String id) {
        assetManager.deleteById(id);
    }

    public void update(Asset entity) {
        assetManager.updateById(entity);
    }

    public Optional<Asset> queryById(String id) {
        return assetManager.queryById(id);
    }

    public Optional<Asset> queryBySn(String sn) {
        return assetManager.queryBySn(sn);
    }

    public PageInfo<Asset> queryPage(Integer pageNum, Integer pageSize, AssetCriteria criteria) {
        return assetManager.queryPage(pageNum, pageSize, criteria);
    }

    public List<Asset> queryAllAsset() {
        return assetManager.listAll();
    }

    public Optional<Asset> queryByDeviceName(String deviceName) {
        return assetManager.queryByDeviceName(deviceName);
    }
}
