package ms.phecda.backend.core.domains.linkage.dao.mapper.typehandler;

import com.trionesdev.commons.mybatisplus.typehandlers.CollectionTypeHandler;
import ms.phecda.backend.core.domains.linkage.support.rule.action.PhecdaAction;

public class ActionsTypeHandler extends CollectionTypeHandler<PhecdaAction> {
    @Override
    protected Class<PhecdaAction> specificType() {
        return PhecdaAction.class;
    }
}
