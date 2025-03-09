package com.trionesdev.phecda.foundation.core.domains.linkage.service.impl;

import cn.hutool.core.map.MapUtil;
import com.google.common.collect.Maps;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.core.util.PageUtils;
import com.trionesdev.phecda.foundation.core.domains.linkage.dao.criteria.LinkageSceneCriteria;
import com.trionesdev.phecda.foundation.core.domains.linkage.dto.LinkageSceneDTO;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.LinkageDomainConvert;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.aggregate.entity.LinkageScene;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.ActionArgs;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.ActionArgs.Reading;
import com.trionesdev.phecda.foundation.core.domains.linkage.manager.impl.LinkageSceneManager;
import com.trionesdev.phecda.foundation.core.domains.linkage.service.factory.RuleActionFactory;
import com.trionesdev.phecda.model.device.PhecdaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.jeasy.rules.api.Facts;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.RuleConstants.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class LinkageSceneService {
    private final LinkageDomainConvert convert;
    private final LinkageSceneManager linkageSceneManager;
    private final RuleActionFactory ruleActionFactory;

    /**
     * 创建场景
     *
     * @param scene
     */
    public void createScene(LinkageScene scene) {
        scene.setEnabled(false);
        linkageSceneManager.create(scene);
    }

    /**
     * 更新场景
     * 更新的时候，会先把规则下线
     *
     * @param scene
     */
    public void updateSceneById(LinkageScene scene) {
        linkageSceneManager.queryById(scene.getId()).ifPresent(t -> {
            if (t.getEnabled()) {
                ruleActionFactory.unregisterRule(scene.getId());
            }
        });
        scene.setEnabled(false);
        linkageSceneManager.updateById(scene);
    }


    /**
     * 删除场景
     *
     * @param id
     */
    public void deleteScene(String id) {
        linkageSceneManager.deleteById(id);
    }

    private LinkageSceneDTO assembleLinkageScene(LinkageScene scene) {
        return convert.linkageSceneEntityToDto(scene);
    }

    public Optional<LinkageSceneDTO> querySceneById(String id) {
        return linkageSceneManager.queryById(id).map(this::assembleLinkageScene);
    }


    private List<LinkageSceneDTO> assembleLinkageScenes(List<LinkageScene> scenes) {
        if (CollectionUtils.isEmpty(scenes)) {
            return Collections.emptyList();
        }
        return scenes.stream().map(t -> {
            return convert.linkageSceneEntityToDto(t);
        }).toList();
    }

    public PageInfo<LinkageSceneDTO> page(LinkageSceneCriteria criteria) {
        var pageInfo = linkageSceneManager.page(criteria);
        return PageUtils.of(pageInfo, assembleLinkageScenes(pageInfo.getRows()));
    }

    /**
     * 场景启用禁用变更
     *
     * @param id
     * @param enabled
     */
    public void sceneEnabledChange(String id, Boolean enabled) {
        linkageSceneManager.queryById(id).ifPresent(t -> {
            LinkageScene linkageScene = LinkageScene.builder().id(id).enabled(enabled).build();
            linkageSceneManager.updateById(linkageScene);
            if (enabled) {
                ruleActionFactory.registerRule(t);
            } else {
                ruleActionFactory.unregisterRule(id);
            }
        });
    }


    public Facts createFacts(PhecdaMessage message) {
        Facts facts = new Facts();
        facts.put(FACT_PRODUCT_KEY, Optional.ofNullable(message.getProductKey()).orElse("nil"));
        facts.put(FACT_DEVICE_NAME, Optional.ofNullable(message.getDeviceName()).orElse("nil"));
        if (MapUtil.isNotEmpty(message.getReadings())) {
            Map<String, Reading> readings = Maps.newHashMap();
            message.getReadings().forEach((k, v) -> {
                facts.put(k, Optional.ofNullable(v.getReadingValue()).orElse("nil"));
                readings.put(k, ActionArgs.Reading.builder().identifier(k).value(v.getReadingValue()).build());
            });
            facts.put(FACT_READINGS, readings);
        }
        return facts;
    }

    /**
     * 触发场景
     *
     * @param message
     */
    public void fireScenes(PhecdaMessage message) {
        try {
            ruleActionFactory.fireRules(createFacts(message));
        } catch (Exception ex) {
            log.error("[ReportPropertyEventHandler] rule fire fail: productKey :{} , message: {}", message.getProductKey(), ex.getMessage(), ex);
        }
    }

}
