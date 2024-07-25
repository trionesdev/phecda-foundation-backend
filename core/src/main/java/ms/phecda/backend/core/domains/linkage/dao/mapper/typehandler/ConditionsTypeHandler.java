package ms.phecda.backend.core.domains.linkage.dao.mapper.typehandler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.trionesdev.commons.mybatisplus.typehandlers.SpecificTypeHandler;
import ms.phecda.backend.core.domains.linkage.internal.rule.othercondition.OtherCondition;

import java.util.List;

@Deprecated
public class ConditionsTypeHandler extends SpecificTypeHandler<List<List<OtherCondition>>> {

    public ConditionsTypeHandler(Class<?> type) {
        super(type);
    }

    @Override
    public TypeReference<List<List<OtherCondition>>> typeReference() {
        return new TypeReference<List<List<OtherCondition>>>() {
        };
    }
}
