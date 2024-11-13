package com.trionesdev.phecda.backend.core.domains.messageforwarding.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.phecda.backend.core.domains.messageforwarding.dao.po.RuleSource;
import com.trionesdev.phecda.backend.core.domains.messageforwarding.dao.mapper.RuleSourceMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class RuleSourceDAO extends ServiceImpl<RuleSourceMapper, RuleSource> {

    public RuleSource selectByUnique(RuleSource ruleSource) {
        return baseMapper.selectOne(new LambdaQueryWrapper<RuleSource>().eq(RuleSource::getSourceId, ruleSource.getSourceId())
                .eq(RuleSource::getRuleId, ruleSource.getRuleId()).last("limit 1")
        );
    }

    public List<RuleSource> selectByRuleId(String ruleId) {
        return baseMapper.selectList(new LambdaQueryWrapper<RuleSource>().eq(RuleSource::getRuleId, ruleId));
    }

    public List<RuleSource> selectByRuleIds(Collection<String> ruleIds) {
        return baseMapper.selectList(new LambdaQueryWrapper<RuleSource>().in(RuleSource::getRuleId, ruleIds));
    }

    public List<RuleSource> selectBySourceId(String sourceId) {
        return baseMapper.selectList(new LambdaQueryWrapper<RuleSource>().eq(RuleSource::getSourceId, sourceId));
    }

    public void delete(RuleSource ruleSource) {
        baseMapper.delete(new LambdaUpdateWrapper<RuleSource>().eq(RuleSource::getRuleId, ruleSource.getRuleId())
                .eq(RuleSource::getSourceId, ruleSource.getSourceId()));
    }
}
