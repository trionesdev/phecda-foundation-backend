package ms.phecda.backend.core.domains.linkage.dao.mapper.typehandler;

import com.trionesdev.commons.mybatisplus.typehandlers.CollectionTypeHandler;
import ms.phecda.backend.core.domains.linkage.support.rule.Scene;

public class ScenesTypeHandler extends CollectionTypeHandler<Scene> {

    @Override
    protected Class<Scene> specificType() {
        return Scene.class;
    }
}
