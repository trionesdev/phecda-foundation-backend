package ms.triones.backend.core.modules.dict.manager;

import com.moensun.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.dict.dao.criteria.DictionaryCriteria;
import ms.triones.backend.core.modules.dict.dao.entity.Dictionary;
import ms.triones.backend.core.modules.dict.dao.impl.DictionaryDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 字典数据表 服务类
 * </p>
 *
 * @author jscoe
 * @since 2023-06-30
 */

@RequiredArgsConstructor
@Service
public class DictionaryManager {
    private final DictionaryDAO dictionaryDAO;

    public void create(Dictionary entity) {
        dictionaryDAO.save(entity);
    }

    public void deleteById(String id) {
        dictionaryDAO.removeById(id);
    }

    public void updateById(Dictionary entity) {
        dictionaryDAO.updateById(entity);
    }

    public Optional<Dictionary> queryById(String id) {
        return Optional.ofNullable(dictionaryDAO.getById(id));
    }

    public PageInfo<Dictionary> queryPage(Integer pageNum, Integer pageSize, DictionaryCriteria criteria) {
        return dictionaryDAO.selectPage(pageNum, pageSize, criteria);
    }

    public List<Dictionary> queryList(DictionaryCriteria criteria) {
        return dictionaryDAO.selectList(criteria);
    }

    public void updateDictionaryType(String oldTypeCode, String typeCode) {
        dictionaryDAO.updateDictionaryType(oldTypeCode, typeCode);
    }
}
