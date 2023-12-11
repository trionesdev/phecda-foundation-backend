package ms.phecda.backend.core.domains.messageforwarding.dao.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MessageForwardingRuleCriteria {
    private Boolean enabled;
}
