package ms.triones.backend.rest.backend.modules.linkage.controller.ro;

import lombok.Data;
import lombok.NonNull;

@Data
public class LinkageSceneEnabledRO {
    @NonNull
    private Boolean enabled;
}
