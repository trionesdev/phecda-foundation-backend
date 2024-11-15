package com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageSourcePO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.mapper.MessageSourceMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class MessageSourceDAO extends ServiceImpl<MessageSourceMapper, MessageSourcePO> {

    public List<MessageSourcePO> selectListByIds(Collection<String> ids){
        return baseMapper.selectBatchIds(ids);
    }

}
