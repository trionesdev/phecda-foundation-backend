package ms.phecda.backend.core.domains.messageforwarding.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageSource;
import ms.phecda.backend.core.domains.messageforwarding.dao.mapper.MessageSourceMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class MessageSourceDAO extends ServiceImpl<MessageSourceMapper, MessageSource> {

    public List<MessageSource> selectListByIds(Collection<String> ids){
        return baseMapper.selectBatchIds(ids);
    }

}
