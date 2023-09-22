package ms.phecda.backend.core.modules.dict.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moensun.commons.core.page.PageInfo;
import com.moensun.commons.mybatisplus.util.MpPageUtils;
import ms.phecda.backend.core.modules.dict.dao.criteria.DictionaryTypeCriteria;
import ms.phecda.backend.core.modules.dict.dao.entity.DictionaryType;
import ms.phecda.backend.core.modules.dict.dao.mapper.DictionaryTypeMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author jscoe
 * @since 2023-06-30
 */

@Repository
public class DictionaryTypeDAO extends ServiceImpl<DictionaryTypeMapper, DictionaryType> {
    private LambdaQueryWrapper<DictionaryType> buildQueryWrapper(DictionaryTypeCriteria criteria) {
        LambdaQueryWrapper<DictionaryType> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(criteria)) {
            queryWrapper.eq(Objects.nonNull(criteria.getCode()), DictionaryType::getCode, criteria.getCode());
            queryWrapper.in(CollectionUtils.isNotEmpty(criteria.getIds()), DictionaryType::getId, criteria.getIds());
            queryWrapper.orderByDesc(DictionaryType::getCreatedAt);
        }
        return queryWrapper;
    }

    public List<DictionaryType> selectList(DictionaryTypeCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria));
    }

    public PageInfo<DictionaryType> selectPage(Integer pageNum, Integer pageSize, DictionaryTypeCriteria criteria) {
        return MpPageUtils.of(baseMapper.selectPage(new Page<>(pageNum, pageSize), buildQueryWrapper(criteria)));
    }

    public Optional<DictionaryType> getByCode(String code) {
        DictionaryType dictionaryType = getOne(buildQueryWrapper(DictionaryTypeCriteria.builder().code(code).build()));
        return Optional.ofNullable(dictionaryType);
    }
}
