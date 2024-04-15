package ms.phecda.backend.core.domains.linkage.internal;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "phecda.linkage")
public class LinkageProperties {
    private Long defaultActionInterval = 6000L;
}
