package ms.phecda.backend.core.domains.messageforwarding.internal;

import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageForwardingRule;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageSourceTopic;
import ms.phecda.backend.core.dto.messageforwarding.MessageForwardingRuleDTO;
import ms.phecda.backend.core.dto.messageforwarding.MessageSourceTopicDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
@Named("messageForwardingBeanConvert")
public interface MessageForwardingBeanConvert {
    MessageForwardingBeanConvert INSTANCE = Mappers.getMapper(MessageForwardingBeanConvert.class);

    MessageSourceTopicDTO dtoFromPo(MessageSourceTopic record);

    List<MessageForwardingRuleDTO> messageForwardingDtoFromEntities(List<MessageForwardingRule> records);

}
