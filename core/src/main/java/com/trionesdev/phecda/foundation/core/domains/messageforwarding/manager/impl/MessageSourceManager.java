package com.trionesdev.phecda.foundation.core.domains.messageforwarding.manager.impl;

import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.MessageSourcePO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.impl.MessageSourceDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class MessageSourceManager {
    private final MessageSourceDAO messageSourceDAO;

    public void create(MessageSourcePO record) {
        messageSourceDAO.save(record);
    }

    public void deleteById(String id){
        Objects.requireNonNull(id);
        messageSourceDAO.removeById(id);
    }
    public void updateById(MessageSourcePO record){
        Objects.requireNonNull(record.getId());
        messageSourceDAO.updateById(record);
    }

    public MessageSourcePO findById(String id){
        return messageSourceDAO.getById(id);
    }

    public List<MessageSourcePO> findAll(){
        return messageSourceDAO.list();
    }

    public List<MessageSourcePO> findListByIds(List<String> ids){
        return messageSourceDAO.selectListByIds(ids);
    }

}
