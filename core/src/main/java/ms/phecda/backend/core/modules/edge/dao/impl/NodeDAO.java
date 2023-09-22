package ms.phecda.backend.core.modules.edge.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ms.phecda.backend.core.modules.edge.dao.entity.Node;
import ms.phecda.backend.core.modules.edge.dao.criteria.NodeCriteria;
import ms.phecda.backend.core.modules.edge.dao.mapper.NodeMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class NodeDAO extends ServiceImpl<NodeMapper, Node> {

    public Page<Node> page(NodeCriteria criteria) {
        LambdaQueryWrapper<Node> queryWrapper = buildQueryWrapper(criteria);
        Page<Node> page = new Page<>(criteria.getPageNum(), criteria.getPageSize());
        return baseMapper.selectPage(page, queryWrapper);
    }

    private LambdaQueryWrapper<Node> buildQueryWrapper(NodeCriteria criteria) {
        LambdaQueryWrapper<Node> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.isNull(criteria)) {
            return queryWrapper;
        }

        queryWrapper.like(StringUtils.isNotBlank(criteria.getName()), Node::getName, criteria.getName());
        return queryWrapper;
    }
}
