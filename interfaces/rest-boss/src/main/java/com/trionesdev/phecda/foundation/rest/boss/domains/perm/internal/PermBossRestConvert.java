package com.trionesdev.phecda.foundation.rest.boss.domains.perm.internal;


import com.trionesdev.phecda.foundation.core.domains.perm.dao.po.FunctionalResourceDraftPO;
import com.trionesdev.phecda.foundation.core.domains.perm.internal.aggregate.entity.FunctionalResource;
import com.trionesdev.phecda.foundation.rest.boss.domains.perm.controller.ro.FunctionalResourceCreateRO;
import com.trionesdev.phecda.foundation.rest.boss.domains.perm.controller.ro.FunctionalResourceDraftCreateRO;
import com.trionesdev.phecda.foundation.rest.boss.domains.perm.controller.ro.FunctionalResourceDraftUpdateRO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, builder = @Builder(disableBuilder = true))
public interface PermBossRestConvert {
    FunctionalResource from(FunctionalResourceCreateRO record);


    FunctionalResourceDraftPO from(FunctionalResourceDraftCreateRO args);

    FunctionalResourceDraftPO from(FunctionalResourceDraftUpdateRO args);
}
