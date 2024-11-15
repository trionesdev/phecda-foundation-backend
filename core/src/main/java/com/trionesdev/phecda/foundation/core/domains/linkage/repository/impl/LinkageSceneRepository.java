package com.trionesdev.phecda.foundation.core.domains.linkage.repository.impl;

import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.core.util.PageUtils;
import com.trionesdev.phecda.foundation.core.domains.linkage.dao.criteria.LinkageSceneCriteria;
import com.trionesdev.phecda.foundation.core.domains.linkage.dao.impl.LinkageSceneDAO;
import com.trionesdev.phecda.foundation.core.domains.linkage.dao.po.LinkageScenePO;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.LinkageDomainConvert;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.aggregate.entity.LinkageScene;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class LinkageSceneRepository {
    private final LinkageDomainConvert convert;
    private final LinkageSceneDAO linkageSceneDAO;

    public void create(LinkageScene linkageScene) {
        var linkageScenePO = convert.linkageSceneEntityToPo(linkageScene);
        linkageSceneDAO.save(linkageScenePO);
        linkageScene.setId(linkageScenePO.getId());
    }

    public void deleteById(String id) {
        linkageSceneDAO.removeById(id);
    }

    public void updateById(LinkageScene linkageScene) {
        Objects.requireNonNull(linkageScene.getId());
        var linkageScenePO = convert.linkageSceneEntityToPo(linkageScene);
        linkageSceneDAO.updateById(linkageScenePO);
    }

    private LinkageScene assembleLinkageScene(LinkageScenePO record) {
        return convert.linkageScenePoToEntity(record);
    }

    private List<LinkageScene> assembleLinkageScenes(List<LinkageScenePO> records) {
        if (CollectionUtils.isEmpty(records)) {
            return Collections.emptyList();
        }
        return records.stream().map(t -> {
            return convert.linkageScenePoToEntity(t);
        }).collect(Collectors.toList());
    }

    public Optional<LinkageScene> findById(String id) {
        return Optional.ofNullable(linkageSceneDAO.getById(id)).map(this::assembleLinkageScene);
    }

    public List<LinkageScene> findList(LinkageSceneCriteria criteria) {
        return assembleLinkageScenes(linkageSceneDAO.selectList(criteria));
    }

    public PageInfo<LinkageScene> findPage(LinkageSceneCriteria criteria) {
        var pageInfo = linkageSceneDAO.page(criteria);
        return PageUtils.of(pageInfo, assembleLinkageScenes(pageInfo.getRows()));
    }

}
