package com.trionesdev.phecda.foundation.rest.tenant.domains.edge.support;

import com.trionesdev.phecda.foundation.core.domains.edge.dao.criteria.NodeCriteria;
import com.trionesdev.phecda.foundation.core.domains.edge.dao.entity.Node;
import com.trionesdev.phecda.foundation.rest.tenant.domains.edge.controller.query.NodeQuery;
import com.trionesdev.phecda.foundation.rest.tenant.domains.edge.controller.ro.NodeCreateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.edge.controller.ro.NodeUpdateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.edge.controller.vo.NodeVO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true))
public interface NodeConvertMapper {
    NodeConvertMapper INSTANT = Mappers.getMapper(NodeConvertMapper.class);

    List<NodeVO> toVoList(List<Node> args);

    Node from(NodeCreateRO args);

    Node from(NodeUpdateRO args);

    NodeVO from(Node args);

    NodeCriteria from(NodeQuery args);
}
