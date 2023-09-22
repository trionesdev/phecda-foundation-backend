package ms.phecda.backend.core.modules.linkage.dao.mapper.typehandler;

import com.moensun.commons.mybatisplus.typehandlers.CollectionTypeHandler;
import ms.phecda.backend.core.modules.linkage.support.rule.action.Action;

public class ActionsTypeHandler extends CollectionTypeHandler<Action> {
    @Override
    protected Class<Action> specificType() {
        return Action.class;
    }
}
