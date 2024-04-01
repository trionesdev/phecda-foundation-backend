package ms.phecda.backend.rest.backend.domains.messageforwarding.support;

import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageForwardingRule;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageSink;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageSource;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageSourceTopic;
import ms.phecda.backend.rest.backend.domains.messageforwarding.controller.ro.*;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface MessageForwardingRestConvertMapper {

    MessageForwardingRestConvertMapper INSTANCE = Mappers.getMapper(MessageForwardingRestConvertMapper.class);

    MessageSource from(MessageSourceCreateRO args);
    MessageSource from(MessageSourceUpdateRO args);

    MessageSourceTopic from(MessageSourceTopicCreateRO args);

    MessageSink from(MessageSinkCreateRO args);
    MessageSink from(MessageSinkUpdateRO args);


    MessageForwardingRule from(MessageForwardingRuleCreateRO args);
    MessageForwardingRule from(MessageForwardingRuleUpdateRO args);

}
