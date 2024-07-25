package ms.phecda.backend.core.domains.linkage.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.mybatisplus.util.MpPageUtils;
import ms.phecda.backend.core.domains.linkage.dao.criteria.LinkageSceneCriteria;
import ms.phecda.backend.core.domains.linkage.dao.entity.LinkageScene;
import ms.phecda.backend.core.domains.linkage.dao.mapper.LinkageSceneMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class LinkageSceneDAO extends ServiceImpl<LinkageSceneMapper, LinkageScene> {

    private LambdaQueryWrapper<LinkageScene> buildQueryWrapper(LinkageSceneCriteria criteria) {
        LambdaQueryWrapper<LinkageScene> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(criteria)) {
            queryWrapper.eq(Objects.nonNull(criteria.getEnabled()), LinkageScene::getEnabled, criteria.getEnabled());
            queryWrapper.like(StringUtils.isNotBlank(criteria.getName()), LinkageScene::getName, criteria.getName());
        }
        return queryWrapper.orderByDesc(LinkageScene::getCreatedAt);
    }

    public List<LinkageScene> selectList(LinkageSceneCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria));
    }

    public PageInfo<LinkageScene> page(LinkageSceneCriteria criteria) {
        LambdaQueryWrapper<LinkageScene> queryWrapper = buildQueryWrapper(criteria);
        return MpPageUtils.of(baseMapper.selectPage(MpPageUtils.page(criteria), queryWrapper));
    }

}
