package com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.RuleSourcePO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.mapper.RuleSourceMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class RuleSourceDAO extends ServiceImpl<RuleSourceMapper, RuleSourcePO> {

    public RuleSourcePO selectByUnique(RuleSourcePO ruleSource) {
        return baseMapper.selectOne(new LambdaQueryWrapper<RuleSourcePO>().eq(RuleSourcePO::getSourceId, ruleSource.getSourceId())
                .eq(RuleSourcePO::getRuleId, ruleSource.getRuleId()).last("limit 1")
        );
    }

    public List<RuleSourcePO> selectByRuleId(String ruleId) {
        return baseMapper.selectList(new LambdaQueryWrapper<RuleSourcePO>().eq(RuleSourcePO::getRuleId, ruleId));
    }

    public List<RuleSourcePO> selectByRuleIds(Collection<String> ruleIds) {
        return baseMapper.selectList(new LambdaQueryWrapper<RuleSourcePO>().in(RuleSourcePO::getRuleId, ruleIds));
    }

    public List<RuleSourcePO> selectBySourceId(String sourceId) {
        return baseMapper.selectList(new LambdaQueryWrapper<RuleSourcePO>().eq(RuleSourcePO::getSourceId, sourceId));
    }

    public void delete(RuleSourcePO ruleSource) {
        baseMapper.delete(new LambdaUpdateWrapper<RuleSourcePO>().eq(RuleSourcePO::getRuleId, ruleSource.getRuleId())
                .eq(RuleSourcePO::getSourceId, ruleSource.getSourceId()));
    }
}
