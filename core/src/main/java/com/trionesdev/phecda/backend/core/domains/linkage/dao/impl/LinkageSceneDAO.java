package com.trionesdev.phecda.backend.core.domains.linkage.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.mybatisplus.util.MpPageUtils;
import com.trionesdev.phecda.backend.core.domains.linkage.dao.criteria.LinkageSceneCriteria;
import com.trionesdev.phecda.backend.core.domains.linkage.dao.po.LinkageScenePO;
import com.trionesdev.phecda.backend.core.domains.linkage.dao.mapper.LinkageSceneMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class LinkageSceneDAO extends ServiceImpl<LinkageSceneMapper, LinkageScenePO> {

    private LambdaQueryWrapper<LinkageScenePO> buildQueryWrapper(LinkageSceneCriteria criteria) {
        LambdaQueryWrapper<LinkageScenePO> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(criteria)) {
            queryWrapper.eq(Objects.nonNull(criteria.getEnabled()), LinkageScenePO::getEnabled, criteria.getEnabled());
            queryWrapper.like(StringUtils.isNotBlank(criteria.getName()), LinkageScenePO::getName, criteria.getName());
        }
        return queryWrapper.orderByDesc(LinkageScenePO::getCreatedAt);
    }

    public List<LinkageScenePO> selectList(LinkageSceneCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria));
    }

    public PageInfo<LinkageScenePO> page(LinkageSceneCriteria criteria) {
        LambdaQueryWrapper<LinkageScenePO> queryWrapper = buildQueryWrapper(criteria);
        return MpPageUtils.of(baseMapper.selectPage(MpPageUtils.page(criteria), queryWrapper));
    }

}
