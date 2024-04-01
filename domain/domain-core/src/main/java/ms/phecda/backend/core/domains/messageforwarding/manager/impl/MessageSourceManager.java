package ms.phecda.backend.core.domains.messageforwarding.manager.impl;

import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageSource;
import ms.phecda.backend.core.domains.messageforwarding.dao.impl.MessageSourceDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class MessageSourceManager {
    private final MessageSourceDAO messageSourceDAO;

    public void create(MessageSource record) {
        messageSourceDAO.save(record);
    }

    public void deleteById(String id){
        Objects.requireNonNull(id);
        messageSourceDAO.removeById(id);
    }
    public void updateById(MessageSource record){
        Objects.requireNonNull(record.getId());
        messageSourceDAO.updateById(record);
    }

    public MessageSource findById(String id){
        return messageSourceDAO.getById(id);
    }

    public List<MessageSource> findAll(){
        return messageSourceDAO.list();
    }

    public List<MessageSource> findListByIds(List<String> ids){
        return messageSourceDAO.selectListByIds(ids);
    }

}
