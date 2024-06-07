package ms.phecda.backend.core.domains.messageforwarding.dao.entity.sinkaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class KafkaSinkAction extends SinkAction {
    private String bootstrapServers;
    private String topic;

}
