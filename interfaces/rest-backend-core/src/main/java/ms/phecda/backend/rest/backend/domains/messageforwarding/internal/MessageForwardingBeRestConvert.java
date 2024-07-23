package ms.phecda.backend.rest.backend.domains.messageforwarding.internal;

import ms.phecda.backend.core.domains.messageforwarding.dao.po.MessageForwardingRule;
import ms.phecda.backend.core.domains.messageforwarding.dao.po.MessageSink;
import ms.phecda.backend.core.domains.messageforwarding.dao.po.MessageSource;
import ms.phecda.backend.core.domains.messageforwarding.dao.po.MessageSourceTopic;
import ms.phecda.backend.rest.backend.domains.messageforwarding.controller.ro.*;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
@Named("messageForwardingBeRestConvert")
public interface MessageForwardingBeRestConvert {

    MessageForwardingBeRestConvert INSTANCE = Mappers.getMapper(MessageForwardingBeRestConvert.class);

    MessageSource from(MessageSourceCreateRO args);

    MessageSource from(MessageSourceUpdateRO args);


    MessageSourceTopic from(MessageSourceTopicRO.Create args);

    MessageSink from(MessageSinkCreateRO args);

    MessageSink from(MessageSinkUpdateRO args);


    MessageForwardingRule from(MessageForwardingRuleCreateRO args);

    MessageForwardingRule from(MessageForwardingRuleUpdateRO args);

}
