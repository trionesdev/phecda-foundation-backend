package com.trionesdev.phecda.foundation.core.domains.edge.support;

import com.trionesdev.phecda.foundation.core.domains.edge.dao.entity.NodeDevice;
import com.trionesdev.phecda.foundation.core.facade.ssp.edge.pdo.NodeDevicePDO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true))
public interface NodeDeviceConvertMapper {
    NodeDeviceConvertMapper INSTANCE = Mappers.getMapper(NodeDeviceConvertMapper.class);

    NodeDevicePDO from(NodeDevice args);

    List<NodeDevicePDO> toPDOList(List<NodeDevice> args);
}
