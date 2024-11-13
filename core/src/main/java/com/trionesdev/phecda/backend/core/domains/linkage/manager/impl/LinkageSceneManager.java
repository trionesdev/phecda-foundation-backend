package com.trionesdev.phecda.backend.core.domains.linkage.manager.impl;

import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.phecda.backend.core.domains.linkage.dao.po.LinkageScenePO;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.backend.core.domains.linkage.dao.criteria.LinkageSceneCriteria;
import com.trionesdev.phecda.backend.core.domains.linkage.dao.impl.LinkageSceneDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LinkageSceneManager {
    private final LinkageSceneDAO linkageSceneDAO;

    public PageInfo<LinkageScenePO> page(LinkageSceneCriteria criteria) {
        return linkageSceneDAO.page(criteria);
    }

    public void create(LinkageScenePO scene) {
        linkageSceneDAO.save(scene);
    }

    public void deleteById(String id) {
        linkageSceneDAO.removeById(id);
    }

    public void updateById(LinkageScenePO scene) {
        linkageSceneDAO.updateById(scene);
    }

    public Optional<LinkageScenePO> queryById(String id) {
        return Optional.ofNullable(linkageSceneDAO.getById(id));
    }

    public List<LinkageScenePO> queryList(LinkageSceneCriteria criteria) {
        return linkageSceneDAO.selectList(criteria);
    }
}
