package ms.triones.backend.core.modules.asset.service.impl;

import com.moensun.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.asset.dao.criteria.SparePartCriteria;
import ms.triones.backend.core.modules.asset.dao.entity.SparePart;
import ms.triones.backend.core.modules.asset.manager.SparePartManager;
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
public class SparePartService {
    private final SparePartManager sparePartManager;
    public void create(SparePart entity) {
        sparePartManager.create(entity);
    }

    public void deleteByIds(List<String> ids) {
        sparePartManager.deleteByIds(ids);
    }

    public void updateById(SparePart entity) {
        sparePartManager.updateById(entity);
    }

    public Optional<SparePart> queryById(String id) {
        return sparePartManager.queryById(id);
    }

    public PageInfo<SparePart> queryPage(Integer pageNum, Integer pageSize,SparePartCriteria criteria) {
        return sparePartManager.queryPage(pageNum, pageSize,criteria);
    }

    public List<SparePart> queryAllSpareParts() {
        return sparePartManager.listAll();
    }

    public Optional<SparePart> queryByDeviceName(String deviceName) {
        return sparePartManager.queryByDeviceName(deviceName);
    }
}
