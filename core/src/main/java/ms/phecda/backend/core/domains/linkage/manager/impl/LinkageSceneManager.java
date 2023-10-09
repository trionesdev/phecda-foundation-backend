package ms.phecda.backend.core.domains.linkage.manager.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moensun.commons.core.page.PageInfo;
import com.moensun.commons.mybatisplus.util.MpPageUtils;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.linkage.dao.criteria.LinkageSceneCriteria;
import ms.phecda.backend.core.domains.linkage.dao.entity.LinkageScene;
import ms.phecda.backend.core.domains.linkage.dao.impl.LinkageSceneDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LinkageSceneManager {
    private final LinkageSceneDAO linkageSceneDAO;

    public PageInfo<LinkageScene> page(LinkageSceneCriteria criteria) {
        IPage<LinkageScene> page = linkageSceneDAO.page(criteria);
        return MpPageUtils.of(page);
    }

    public void create(LinkageScene scene) {
        linkageSceneDAO.save(scene);
    }

    public void deleteById(String id) {
        linkageSceneDAO.removeById(id);
    }

    public void updateById(LinkageScene scene) {
        linkageSceneDAO.updateById(scene);
    }

    public Optional<LinkageScene> queryById(String id) {
        return Optional.ofNullable(linkageSceneDAO.getById(id));
    }

    public List<LinkageScene> queryList(LinkageSceneCriteria criteria) {
        return linkageSceneDAO.selectList(criteria);
    }
}
