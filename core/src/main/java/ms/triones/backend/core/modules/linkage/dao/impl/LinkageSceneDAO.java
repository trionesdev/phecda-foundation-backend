package ms.triones.backend.core.modules.linkage.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ms.triones.backend.core.modules.linkage.dao.criteria.LinkageSceneCriteria;
import ms.triones.backend.core.modules.linkage.dao.entity.LinkageScene;
import ms.triones.backend.core.modules.linkage.dao.mapper.LinkageSceneMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class LinkageSceneDAO extends ServiceImpl<LinkageSceneMapper, LinkageScene> {
    private LambdaQueryWrapper<LinkageScene> buildQueryWrapper(LinkageSceneCriteria criteria) {
        LambdaQueryWrapper<LinkageScene> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(criteria)) {
            queryWrapper.eq(Objects.nonNull(criteria.getEnabled()), LinkageScene::getEnabled, criteria.getEnabled())
            ;
        }
        return queryWrapper;
    }

    public List<LinkageScene> selectList(LinkageSceneCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria));
    }

}
