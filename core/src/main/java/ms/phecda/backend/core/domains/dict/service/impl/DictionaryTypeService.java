package ms.phecda.backend.core.domains.dict.service.impl;

import com.moensun.commons.core.page.PageInfo;
import com.moensun.commons.exception.MSException;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.dict.dao.criteria.DictionaryCriteria;
import ms.phecda.backend.core.domains.dict.dao.criteria.DictionaryTypeCriteria;
import ms.phecda.backend.core.domains.dict.dao.entity.Dictionary;
import ms.phecda.backend.core.domains.dict.dao.entity.DictionaryType;
import ms.phecda.backend.core.domains.dict.manager.DictionaryManager;
import ms.phecda.backend.core.domains.dict.manager.DictionaryTypeManager;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
public class DictionaryTypeService {
    private final DictionaryTypeManager dictionaryTypeManager;
    private final DictionaryManager dictionaryManager;

    public void create(DictionaryType entity) {
        checkCode(entity);
        dictionaryTypeManager.create(entity);
    }

    public void deleteByIds(List<String> ids) {
        List<DictionaryType> dictionaryTypes = dictionaryTypeManager.queryList(DictionaryTypeCriteria.builder().ids(ids).build());
        List<String> codes = dictionaryTypes.stream().map(DictionaryType::getCode).collect(Collectors.toList());
        List<Dictionary> dictionaries = dictionaryManager.queryList(DictionaryCriteria.builder().typeCodes(codes).build());
        Set<String> codeSet = dictionaries.stream().map(Dictionary::getTypeCode).collect(Collectors.toSet());
        if (CollectionUtils.isNotEmpty(codeSet)) {
            throw new MSException("编号[" + StringUtils.join(codeSet.toArray(), ",") + "]已分配,不能删除");
        }
        dictionaryTypeManager.deleteByIds(ids);
    }

    @Transactional
    public void updateById(DictionaryType entity) {
        checkCode(entity);
        Optional<DictionaryType> dictionaryTypeOpt = dictionaryTypeManager.queryById(entity.getId());
        dictionaryTypeOpt.ifPresent(type -> dictionaryManager.updateDictionaryType(type.getCode(), entity.getCode()));
        dictionaryTypeManager.updateById(entity);
    }

    public Optional<DictionaryType> queryById(String id) {
        return dictionaryTypeManager.queryById(id);
    }

    public PageInfo<DictionaryType> queryPage(Integer pageNum, Integer pageSize, DictionaryTypeCriteria criteria) {
        return dictionaryTypeManager.queryPage(pageNum, pageSize, criteria);
    }

    private void checkCode(DictionaryType entity) {
        if (StringUtils.isBlank(entity.getCode())) {
            return;
        }

        Optional<DictionaryType> dictionaryTypeOpt = dictionaryTypeManager.getByCode(entity.getCode());
        if (!dictionaryTypeOpt.isPresent()) {
            return;
        }

        if (!Objects.equals(dictionaryTypeOpt.get().getId(), entity.getId())) {
            throw new MSException("编号[" + entity.getCode() + "]已被使用");
        }
    }
}
