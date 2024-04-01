package ms.phecda.backend.core.domains.messageforwarding.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ms.phecda.backend.core.domains.messageforwarding.dao.criteria.MessageForwardingRuleCriteria;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageForwardingRule;
import ms.phecda.backend.core.domains.messageforwarding.dao.mapper.MessageForwardingRuleMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class MessageForwardingRuleDAO extends ServiceImpl<MessageForwardingRuleMapper, MessageForwardingRule> {

    private LambdaQueryWrapper<MessageForwardingRule> buildQueryWrapper(MessageForwardingRuleCriteria criteria) {
        LambdaQueryWrapper<MessageForwardingRule> queryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(criteria)) {
            queryWrapper.eq(Objects.nonNull(criteria.getEnabled()), MessageForwardingRule::getEnabled, criteria.getEnabled())
            ;
        }
        return queryWrapper;
    }

    public List<MessageForwardingRule> selectList(MessageForwardingRuleCriteria criteria) {
        return this.baseMapper.selectList(buildQueryWrapper(criteria));
    }

}
