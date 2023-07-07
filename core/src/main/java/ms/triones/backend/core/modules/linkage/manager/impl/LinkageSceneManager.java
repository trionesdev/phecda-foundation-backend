package ms.triones.backend.core.modules.linkage.manager.impl;

import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.linkage.dao.criteria.LinkageSceneCriteria;
import ms.triones.backend.core.modules.linkage.dao.entity.LinkageScene;
import ms.triones.backend.core.modules.linkage.dao.impl.LinkageSceneDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LinkageSceneManager {
    private final LinkageSceneDAO linkageSceneDAO;

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
