package com.trionesdev.phecda.foundation.rest.tenant.domains.linkage.controller.ro;

import lombok.Data;
import lombok.NonNull;

@Data
public class LinkageSceneEnabledRO {
    @NonNull
    private Boolean enabled;
}
