package com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.RuleSink;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.mapper.RuleSinkMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class RuleSinkDAO extends ServiceImpl<RuleSinkMapper, RuleSink> {

    public RuleSink selectByUnique(RuleSink ruleSink) {
        return baseMapper.selectOne(new LambdaQueryWrapper<RuleSink>()
                .eq(RuleSink::getRuleId, ruleSink.getRuleId()).eq(RuleSink::getSinkId, ruleSink.getSinkId()).last(" limit 1 "));
    }

    public List<RuleSink> selectListByRuleId(String ruleId) {
        return baseMapper.selectList(new LambdaQueryWrapper<RuleSink>().eq(RuleSink::getRuleId, ruleId));
    }

    public List<RuleSink> selectListByRuleIds(Collection<String> ruleIds) {
        return baseMapper.selectList(new LambdaQueryWrapper<RuleSink>().in(RuleSink::getRuleId, ruleIds));
    }


    public List<RuleSink> selectListBySinkId(String sinkId) {
        return baseMapper.selectList(new LambdaQueryWrapper<RuleSink>().eq(RuleSink::getSinkId, sinkId));
    }

    public void delete(RuleSink ruleSink) {
        baseMapper.delete(new LambdaUpdateWrapper<RuleSink>()
                .eq(RuleSink::getRuleId, ruleSink.getRuleId()).eq(RuleSink::getSinkId, ruleSink.getSinkId()));
    }

}
