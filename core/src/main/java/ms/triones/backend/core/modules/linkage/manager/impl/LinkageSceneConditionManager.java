package ms.triones.backend.core.modules.linkage.manager.impl;

import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.linkage.dao.impl.LinkageSceneConditionDAO;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LinkageSceneConditionManager {
    private final LinkageSceneConditionDAO linkageSceneConditionDAO;
}
