package ms.triones.backend.core.modules.asset.manager;

import com.moensun.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.asset.dao.criteria.AssetCriteria;
import ms.triones.backend.core.modules.asset.dao.criteria.SparePartCriteria;
import ms.triones.backend.core.modules.asset.dao.entity.Asset;
import ms.triones.backend.core.modules.asset.dao.entity.SparePart;
import ms.triones.backend.core.modules.asset.dao.impl.SparePartDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 资产配件 服务类
 * </p>
 *
 * @author jscoe
 * @since 2023-06-30
 */

@RequiredArgsConstructor
@Service
public class SparePartManager {
    private final SparePartDAO sparePartDAO;

    public void create(SparePart entity) {
        sparePartDAO.save(entity);
    }

    public void deleteByIds(List<String> ids) {
        sparePartDAO.removeByIds(ids);
    }

    public void updateById(SparePart entity) {
        sparePartDAO.updateById(entity);
    }

    public Optional<SparePart> queryById(String id) {
        return Optional.ofNullable(sparePartDAO.getById(id));
    }

    public PageInfo<SparePart> queryPage(Integer pageNum, Integer pageSize, SparePartCriteria criteria) {
        return sparePartDAO.selectPage(pageNum, pageSize, criteria);
    }

    public List<SparePart> queryList(SparePartCriteria criteria) {
        return sparePartDAO.selectList(criteria);
    }
}
