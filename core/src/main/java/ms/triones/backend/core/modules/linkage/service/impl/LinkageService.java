package ms.triones.backend.core.modules.linkage.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.triones.backend.core.modules.linkage.manager.impl.LinkageSceneConditionManager;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LinkageService {
    private final RulesEngine rulesEngine = new DefaultRulesEngine();
    private final Rules linkageRules = new Rules();
    private final LinkageSceneConditionManager linkageSceneConditionManager;

    public void say() {
        log.error("sssss");
    }

    public void loadRules() {

    }

}
