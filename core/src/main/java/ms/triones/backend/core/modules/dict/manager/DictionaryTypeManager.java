package ms.triones.backend.core.modules.dict.manager;

import com.moensun.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.dict.dao.criteria.DictionaryTypeCriteria;
import ms.triones.backend.core.modules.dict.dao.entity.DictionaryType;
import ms.triones.backend.core.modules.dict.dao.impl.DictionaryTypeDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 字典类型表 服务类
 * </p>
 *
 * @author jscoe
 * @since 2023-06-30
 */

@RequiredArgsConstructor
@Service
public class DictionaryTypeManager {
    private final DictionaryTypeDAO dictionaryTypeDAO;

    public void create(DictionaryType entity) {
        dictionaryTypeDAO.save(entity);
    }

    public void deleteByIds(List<String> ids) {
        dictionaryTypeDAO.removeByIds(ids);
    }

    public void updateById(DictionaryType entity) {
        dictionaryTypeDAO.updateById(entity);
    }

    public Optional<DictionaryType> queryById(String id) {
        return Optional.ofNullable(dictionaryTypeDAO.getById(id));
    }

    public PageInfo<DictionaryType> queryPage(Integer pageNum, Integer pageSize, DictionaryTypeCriteria criteria) {
        return dictionaryTypeDAO.selectPage(pageNum, pageSize, criteria);
    }

    public List<DictionaryType> queryList(DictionaryTypeCriteria criteria) {
        return dictionaryTypeDAO.selectList(criteria);
    }

    public Optional<DictionaryType> getByCode(String code) {
        return dictionaryTypeDAO.getByCode(code);
    }
}
