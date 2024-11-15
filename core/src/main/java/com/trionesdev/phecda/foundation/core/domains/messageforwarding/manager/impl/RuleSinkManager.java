package com.trionesdev.phecda.foundation.core.domains.messageforwarding.manager.impl;

import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po.RuleSinkPO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.impl.RuleSinkDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class RuleSinkManager {
    private final RuleSinkDAO ruleSinkDAO;

    public void create(RuleSinkPO ruleSink) {
        RuleSinkPO ruleSinkSnap = ruleSinkDAO.selectByUnique(ruleSink);
        if (Objects.nonNull(ruleSinkSnap)) {
            return;
        }
        ruleSinkDAO.save(ruleSink);
    }

    public List<RuleSinkPO> findListByRuleId(String ruleId) {
        return ruleSinkDAO.selectListByRuleId(ruleId);
    }

    public List<RuleSinkPO> findListBySinkId(String sinkId) {
        return ruleSinkDAO.selectListBySinkId(sinkId);
    }


    public void delete(RuleSinkPO ruleSink) {
        ruleSinkDAO.delete(ruleSink);
    }

}
