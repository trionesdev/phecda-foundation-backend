package ms.phecda.backend.core.domains.dict.service.impl;

import com.moensun.commons.core.page.PageInfo;
import com.moensun.commons.exception.MSException;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.dict.dao.criteria.DictionaryCriteria;
import ms.phecda.backend.core.domains.dict.dao.entity.Dictionary;
import ms.phecda.backend.core.domains.dict.manager.DictionaryManager;
import ms.phecda.backend.core.domains.dict.service.bo.DictionaryBO;
import ms.phecda.backend.core.domains.dict.support.DictionaryConvertMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (Objects.nonNull(entity) && Objects.isNull(entity.getParentCode())) {
            entity.setParentCode("0");
        }
        List<Dictionary> dictionaries = queryList(DictionaryCriteria.builder().code(entity.getCode()).build());

        if (!CollectionUtils.isEmpty(dictionaries)) {
            throw new MSException(String.format("编码：{%s}已存在", entity.getCode()));
        }
        dictionaryManager.create(entity);
    }

    public void deleteById(String id) {
        dictionaryManager.deleteById(id);
    }

    public void update(Dictionary entity) {
        List<Dictionary> dictionaries = queryList(DictionaryCriteria.builder().code(entity.getCode()).build());
        if (Objects.nonNull(dictionaries)) {
            throw new MSException(String.format("编码：{%s}已存在", entity.getCode()));
        }
        dictionaryManager.updateById(entity);
    }

    public Optional<Dictionary> queryById(String id) {
        return dictionaryManager.queryById(id);
    }

    public PageInfo<DictionaryBO> queryPage(Integer pageNum, Integer pageSize, DictionaryCriteria criteria) {
        criteria.setParentCode("0");
        PageInfo<Dictionary> dictionaryPageInfo = dictionaryManager.queryPage(pageNum, pageSize, criteria);
        List<Dictionary> dictionaries = dictionaryPageInfo.getRows();
        List<DictionaryBO> dictionaryBOs = DictionaryConvertMapper.INSTANT.productBOFromRecord(dictionaries);

        criteria.setParentCode(null);
        List<Dictionary> all = queryList(criteria);
        List<DictionaryBO> allBOs = DictionaryConvertMapper.INSTANT.productBOFromRecord(all);
        List<DictionaryBO> rows = getChildrenList(dictionaryBOs, allBOs);

        return PageInfo.<DictionaryBO>builder().pageNum(dictionaryPageInfo.getPageNum())
                .pageSize(dictionaryPageInfo.getPageSize())
                .pages(dictionaryPageInfo.getPages())
                .total(dictionaryPageInfo.getTotal())
                .rows(rows)
                .build();
    }

    public List<Dictionary> queryList(DictionaryCriteria criteria) {
        return dictionaryManager.queryList(criteria);
    }

    private List<DictionaryBO> getChildrenList(List<DictionaryBO> roots, List<DictionaryBO> all) {
        roots.forEach(root -> {
            List<DictionaryBO> childernList = all.stream().filter(temp -> temp.getParentCode().equals(root.getCode())).collect(Collectors.toList());
            root.setChildren(getChildrenList(childernList, all));
        });
        return roots;
    }

}
