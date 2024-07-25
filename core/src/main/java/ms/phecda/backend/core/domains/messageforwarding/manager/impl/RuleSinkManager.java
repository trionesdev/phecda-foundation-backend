package ms.phecda.backend.core.domains.messageforwarding.manager.impl;

import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.messageforwarding.dao.po.RuleSink;
import ms.phecda.backend.core.domains.messageforwarding.dao.impl.RuleSinkDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class RuleSinkManager {
    private final RuleSinkDAO ruleSinkDAO;

    public void create(RuleSink ruleSink) {
        RuleSink ruleSinkSnap = ruleSinkDAO.selectByUnique(ruleSink);
        if (Objects.nonNull(ruleSinkSnap)) {
            return;
        }
        ruleSinkDAO.save(ruleSink);
    }

    public List<RuleSink> findListByRuleId(String ruleId) {
        return ruleSinkDAO.selectListByRuleId(ruleId);
    }

    public List<RuleSink> findListBySinkId(String sinkId) {
        return ruleSinkDAO.selectListBySinkId(sinkId);
    }


    public void delete(RuleSink ruleSink) {
        ruleSinkDAO.delete(ruleSink);
    }

}
