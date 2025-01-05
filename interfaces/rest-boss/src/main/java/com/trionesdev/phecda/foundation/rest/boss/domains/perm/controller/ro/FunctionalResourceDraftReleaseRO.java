package com.trionesdev.phecda.foundation.rest.boss.domains.perm.controller.ro;

import com.trionesdev.phecda.foundation.core.domains.perm.internal.enums.ClientType;
import lombok.Data;

@Data
public class FunctionalResourceDraftReleaseRO {
    private String appIdentifier;
    private ClientType clientType;
}
