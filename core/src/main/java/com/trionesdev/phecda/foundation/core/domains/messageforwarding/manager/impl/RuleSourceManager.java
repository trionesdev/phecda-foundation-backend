package com.trionesdev.phecda.foundation.core.domains.messageforwarding.manager.impl;

import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.RuleSource;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.impl.RuleSourceDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class RuleSourceManager {
    private final RuleSourceDAO ruleSourceDAO;

    public void create(RuleSource ruleSource) {
        RuleSource ruleSourceSnap = ruleSourceDAO.selectByUnique(ruleSource);
        if (Objects.nonNull(ruleSourceSnap)) {
            return;
        }
        ruleSourceDAO.save(ruleSource);
    }

    public void delete(RuleSource ruleSource){
        ruleSourceDAO.delete(ruleSource);
    }

    public List<RuleSource> findListByRuleId(String ruleId) {
        return ruleSourceDAO.selectByRuleId(ruleId);
    }

    public List<RuleSource> findListBySourceId(String sourceId) {
        return ruleSourceDAO.selectBySourceId(sourceId);
    }


}
