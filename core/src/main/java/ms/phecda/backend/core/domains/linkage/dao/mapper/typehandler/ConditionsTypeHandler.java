package ms.phecda.backend.core.domains.linkage.dao.mapper.typehandler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.moensun.commons.mybatisplus.typehandlers.SpecificTypeHandler;
import ms.phecda.backend.core.domains.linkage.support.rule.othercondition.OtherCondition;

import java.util.List;

@Deprecated
public class ConditionsTypeHandler extends SpecificTypeHandler<List<List<OtherCondition>>> {

    @Override
    public TypeReference<List<List<OtherCondition>>> typeReference() {
        return new TypeReference<List<List<OtherCondition>>>() {
        };
    }
}
