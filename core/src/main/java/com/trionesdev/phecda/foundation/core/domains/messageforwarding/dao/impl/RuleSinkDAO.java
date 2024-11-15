package com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.RuleSinkPO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.mapper.RuleSinkMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class RuleSinkDAO extends ServiceImpl<RuleSinkMapper, RuleSinkPO> {

    public RuleSinkPO selectByUnique(RuleSinkPO ruleSink) {
        return baseMapper.selectOne(new LambdaQueryWrapper<RuleSinkPO>()
                .eq(RuleSinkPO::getRuleId, ruleSink.getRuleId()).eq(RuleSinkPO::getSinkId, ruleSink.getSinkId()).last(" limit 1 "));
    }

    public List<RuleSinkPO> selectListByRuleId(String ruleId) {
        return baseMapper.selectList(new LambdaQueryWrapper<RuleSinkPO>().eq(RuleSinkPO::getRuleId, ruleId));
    }

    public List<RuleSinkPO> selectListByRuleIds(Collection<String> ruleIds) {
        return baseMapper.selectList(new LambdaQueryWrapper<RuleSinkPO>().in(RuleSinkPO::getRuleId, ruleIds));
    }


    public List<RuleSinkPO> selectListBySinkId(String sinkId) {
        return baseMapper.selectList(new LambdaQueryWrapper<RuleSinkPO>().eq(RuleSinkPO::getSinkId, sinkId));
    }

    public void delete(RuleSinkPO ruleSink) {
        baseMapper.delete(new LambdaUpdateWrapper<RuleSinkPO>()
                .eq(RuleSinkPO::getRuleId, ruleSink.getRuleId()).eq(RuleSinkPO::getSinkId, ruleSink.getSinkId()));
    }

}
