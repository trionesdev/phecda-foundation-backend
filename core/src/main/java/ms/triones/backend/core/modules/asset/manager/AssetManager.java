package ms.triones.backend.core.modules.asset.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.moensun.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.asset.dao.criteria.AssetCriteria;
import ms.triones.backend.core.modules.asset.dao.entity.Asset;
import ms.triones.backend.core.modules.asset.dao.impl.AssetDAO;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
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
public class AssetManager {
    private final AssetDAO assetDAO;

    public void create(Asset entity) {
        assetDAO.save(entity);
    }

    public void deleteById(String id) {
        assetDAO.removeById(id);
    }

    public void updateById(Asset entity) {
        assetDAO.updateById(entity);
    }

    public Optional<Asset> queryById(String id) {
        return Optional.ofNullable(assetDAO.getById(id));
    }

    public Optional<Asset> queryBySn(String sn) {
        return assetDAO.getBySn(sn);
    }

    public PageInfo<Asset> queryPage(Integer pageNum, Integer pageSize, AssetCriteria criteria) {
        return assetDAO.selectPage(pageNum, pageSize, criteria);
    }

    public List<Asset> listAll() {
        return assetDAO.list();
    }

    public List<Asset> queryList(AssetCriteria criteria) {
        return assetDAO.selectList(criteria);
    }

}
