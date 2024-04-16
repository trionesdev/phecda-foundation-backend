package ms.phecda.backend.core.domains.messageforwarding.internal;

import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageForwardingRule;
import ms.phecda.backend.core.domains.messageforwarding.manager.dto.MessageForwardingRuleDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true))
public interface MessageForwardingConvertMapper {
    MessageForwardingConvertMapper INSTANCE = Mappers.getMapper(MessageForwardingConvertMapper.class);


    List<MessageForwardingRuleDTO> messageForwardingDtoFromEntities(List<MessageForwardingRule> records);

}
