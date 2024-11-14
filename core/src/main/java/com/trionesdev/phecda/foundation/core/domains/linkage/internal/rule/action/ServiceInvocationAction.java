package com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceInvocationAction extends PhecdaAction {
    private String productKey;
    private String deviceName;
    private String serviceIdentifier;
    private String params;
    private String body;

    @Override
    public TypeEnum getType() {
        return TypeEnum.SERVICE_INVOCATION;
    }
}
