package ms.phecda.backend.core.domains.messageforwarding.internal;

import ms.phecda.backend.core.domains.messageforwarding.dao.po.MessageForwardingRule;
import ms.phecda.backend.core.domains.messageforwarding.dao.po.MessageSourceTopic;
import ms.phecda.backend.core.domains.messageforwarding.dto.MessageForwardingRuleDTO;
import ms.phecda.backend.core.domains.messageforwarding.dto.MessageSourceTopicDTO;
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
