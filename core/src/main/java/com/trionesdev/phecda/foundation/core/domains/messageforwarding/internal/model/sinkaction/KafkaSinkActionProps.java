package com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.model.sinkaction;

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
public class KafkaSinkActionProps extends SinkActionProps {
    private String bootstrapServers;
    private String topic;

}
