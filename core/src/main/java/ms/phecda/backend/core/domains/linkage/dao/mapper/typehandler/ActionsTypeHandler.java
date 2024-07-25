package ms.phecda.backend.core.domains.linkage.dao.mapper.typehandler;

import com.trionesdev.commons.mybatisplus.typehandlers.CollectionTypeHandler;
import ms.phecda.backend.core.domains.linkage.internal.rule.action.PhecdaAction;

public class ActionsTypeHandler extends CollectionTypeHandler<PhecdaAction> {
    public ActionsTypeHandler(Class<?> type) {
        super(type);
    }

    @Override
    protected Class<PhecdaAction> specificType() {
        return PhecdaAction.class;
    }
}
