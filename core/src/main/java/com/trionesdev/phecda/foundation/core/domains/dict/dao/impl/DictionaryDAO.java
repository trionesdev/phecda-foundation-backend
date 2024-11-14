package com.trionesdev.phecda.foundation.core.domains.dict.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.mybatisplus.util.MpPageUtils;
import com.trionesdev.phecda.foundation.core.domains.dict.dao.criteria.DictionaryCriteria;
import com.trionesdev.phecda.foundation.core.domains.dict.dao.entity.Dictionary;
import com.trionesdev.phecda.foundation.core.domains.dict.dao.mapper.DictionaryMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author jscoe
 * @since 2023-06-30
 */

@Repository
public class DictionaryDAO extends ServiceImpl<DictionaryMapper, Dictionary> {

    private LambdaQueryWrapper<Dictionary> buildQueryWrapper(DictionaryCriteria criteria) {
        LambdaQueryWrapper<Dictionary> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(criteria)) {
            queryWrapper.eq(StringUtils.isNotBlank(criteria.getTypeCode()), Dictionary::getTypeCode, criteria.getTypeCode());
            queryWrapper.eq(StringUtils.isNotBlank(criteria.getParentCode()), Dictionary::getParentCode, criteria.getParentCode());
            queryWrapper.eq(StringUtils.isNotBlank(criteria.getCode()), Dictionary::getCode, criteria.getCode());
            queryWrapper.in(CollectionUtils.isNotEmpty(criteria.getTypeCodes()), Dictionary::getTypeCode, criteria.getTypeCodes());
        }
        return queryWrapper.orderByAsc(Dictionary::getSort);
    }

    public PageInfo<Dictionary> selectPage(Integer pageNum, Integer pageSize, DictionaryCriteria criteria) {
        return MpPageUtils.of(baseMapper.selectPage(new Page<>(pageNum, pageSize), buildQueryWrapper(criteria)));
    }

    public List<Dictionary> selectList(DictionaryCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria));
    }

    public void updateDictionaryType(String oldTypeCode, String typeCode) {
        Dictionary updateEntity = Dictionary.builder().typeCode(typeCode).build();
        LambdaQueryWrapper<Dictionary> whereWrapper = Wrappers.lambdaQuery();
        whereWrapper.eq(Dictionary::getTypeCode, oldTypeCode);
        baseMapper.update(updateEntity, whereWrapper);
    }
}
