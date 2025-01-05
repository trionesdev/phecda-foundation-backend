package com.trionesdev.phecda.foundation.rest.tenant.domains.base.internal;

import com.trionesdev.phecda.foundation.core.domains.base.internal.aggregate.entity.CodeFormatRule;
import com.trionesdev.phecda.foundation.rest.tenant.domains.base.controller.ro.CodeFormatRuleCreateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.base.controller.ro.CodeFormatRuleUpdateRO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, builder = @Builder(disableBuilder = true))
public interface BaseTenantRestConvert {
    CodeFormatRule from(CodeFormatRuleCreateRO args);

    CodeFormatRule from(CodeFormatRuleUpdateRO args);
}
