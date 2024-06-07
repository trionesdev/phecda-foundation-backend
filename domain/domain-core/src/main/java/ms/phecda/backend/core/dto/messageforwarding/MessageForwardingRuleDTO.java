package ms.phecda.backend.core.dto.messageforwarding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageForwardingRule;
import ms.phecda.backend.core.domains.messageforwarding.dao.entity.MessageSink;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MessageForwardingRuleDTO extends MessageForwardingRule {
    private MessageSourceDTO source;
    private Collection<MessageSink> sinks;
}
