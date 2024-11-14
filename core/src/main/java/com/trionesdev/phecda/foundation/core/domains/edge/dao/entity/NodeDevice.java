package com.trionesdev.phecda.foundation.core.domains.edge.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trionesdev.commons.mybatisplus.entity.BaseLogicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@SuperBuilder
@TableName(value = "phecda_edge_node_device", autoResultMap = true)
public class NodeDevice extends BaseLogicEntity {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    private String nodeId;
    private String deviceId;
}
