package com.trionesdev.phecda.foundation.core.domains.linkage.manager.impl;

import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.aggregate.entity.LinkageScene;
import com.trionesdev.phecda.foundation.core.domains.linkage.repository.impl.LinkageSceneRepository;
import com.trionesdev.phecda.foundation.core.domains.linkage.dao.criteria.LinkageSceneCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LinkageSceneManager {
    private final LinkageSceneRepository linkageSceneRepository;


    public void create(LinkageScene scene) {
        linkageSceneRepository.create(scene);
    }

    public void deleteById(String id) {
        linkageSceneRepository.deleteById(id);
    }

    public void updateById(LinkageScene scene) {
        linkageSceneRepository.updateById(scene);
    }

    public Optional<LinkageScene> queryById(String id) {
        return linkageSceneRepository.findById(id);
    }

    public List<LinkageScene> queryList(LinkageSceneCriteria criteria) {
        return linkageSceneRepository.findList(criteria);
    }

    public PageInfo<LinkageScene> page(LinkageSceneCriteria criteria) {
        return linkageSceneRepository.findPage(criteria);
    }
}
