package com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.criteria.MessageForwardingRuleCriteria;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageForwardingRulePO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.mapper.MessageForwardingRuleMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class MessageForwardingRuleDAO extends ServiceImpl<MessageForwardingRuleMapper, MessageForwardingRulePO> {

    private LambdaQueryWrapper<MessageForwardingRulePO> buildQueryWrapper(MessageForwardingRuleCriteria criteria) {
        LambdaQueryWrapper<MessageForwardingRulePO> queryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(criteria)) {
            queryWrapper.eq(Objects.nonNull(criteria.getEnabled()), MessageForwardingRulePO::getEnabled, criteria.getEnabled())
            ;
        }
        return queryWrapper;
    }

    public List<MessageForwardingRulePO> selectList(MessageForwardingRuleCriteria criteria) {
        return this.baseMapper.selectList(buildQueryWrapper(criteria));
    }

}
