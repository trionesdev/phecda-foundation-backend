package com.trionesdev.phecda.backend.core.domains.edge.support;

import com.trionesdev.phecda.backend.core.domains.edge.dao.entity.Node;
import com.trionesdev.phecda.backend.core.provider.ssp.edge.pdo.NodePDO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface NodeConvertMapper {
    NodeConvertMapper INSTANCE = Mappers.getMapper(NodeConvertMapper.class);

    NodePDO toPDO(Node args);
}
