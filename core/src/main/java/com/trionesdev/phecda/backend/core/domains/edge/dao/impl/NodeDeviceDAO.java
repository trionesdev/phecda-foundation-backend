package com.trionesdev.phecda.backend.core.domains.edge.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.phecda.backend.core.domains.edge.dao.entity.NodeDevice;
import com.trionesdev.phecda.backend.core.domains.edge.dao.mapper.NodeDeviceMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class NodeDeviceDAO extends ServiceImpl<NodeDeviceMapper, NodeDevice> {

    public List<NodeDevice> listByNodeId(String nodeId) {
        LambdaQueryWrapper<NodeDevice> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(NodeDevice::getNodeId, nodeId);
        return list(queryWrapper);
    }

    public List<NodeDevice> listByDeviceId(List<String> deviceIds) {
        if (CollectionUtils.isEmpty(deviceIds)) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<NodeDevice> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(NodeDevice::getDeviceId, deviceIds);
        return list(queryWrapper);
    }

    public void remove(String nodeId, List<String> deviceIds) {
        if (CollectionUtils.isEmpty(deviceIds)) {
            return;
        }

        LambdaQueryWrapper<NodeDevice> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(NodeDevice::getNodeId, nodeId);
        queryWrapper.in(NodeDevice::getDeviceId, deviceIds);
        remove(queryWrapper);
    }

    public NodeDevice getByDeviceId(String deviceId) {
        LambdaQueryWrapper<NodeDevice> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(NodeDevice::getDeviceId, deviceId);
        return getOne(queryWrapper);
    }
}
