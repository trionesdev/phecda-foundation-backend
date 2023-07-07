package ms.triones.backend.core.modules.dict.service.impl;

import com.moensun.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.dict.dao.criteria.DictionaryCriteria;
import ms.triones.backend.core.modules.dict.dao.entity.Dictionary;
import ms.triones.backend.core.modules.dict.manager.DictionaryManager;
import org.springframework.stereotype.Service;

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
public class DictionaryService {
    private final DictionaryManager dictionaryManager;

    public void create(Dictionary entity) {
        dictionaryManager.create(entity);
    }

    public void deleteById(String id) {
        dictionaryManager.deleteById(id);
    }

    public void update(Dictionary entity) {
        dictionaryManager.updateById(entity);
    }

    public Optional<Dictionary> queryById(String id) {
        return dictionaryManager.queryById(id);
    }

    public PageInfo<Dictionary> queryPage(Integer pageNum, Integer pageSize, DictionaryCriteria criteria) {
        return dictionaryManager.queryPage(pageNum, pageSize, criteria);
    }
}
