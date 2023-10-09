package ms.phecda.backend.rest.backend.domains.linkage.controller.ro;

import lombok.Data;
import lombok.NonNull;

@Data
public class LinkageSceneEnabledRO {
    @NonNull
    private Boolean enabled;
}
