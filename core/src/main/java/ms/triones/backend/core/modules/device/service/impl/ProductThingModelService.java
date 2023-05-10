package ms.triones.backend.core.modules.device.service.impl;

import cn.hutool.core.util.StrUtil;
import com.moensun.commons.exception.spring.ex.BusinessException;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.device.dao.entity.ProductThingModel;
import ms.triones.backend.core.modules.device.manager.impl.ProductThingModelManager;
import ms.triones.backend.core.modules.device.thing.model.ThingModel;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ProductThingModelService {
    private final ProductThingModelManager productThingModelManager;

    public void upsertThingModel(String productId, String abilityType, String identifier, ThingModel thingModel) {
        ProductThingModel ptmSnap = productThingModelManager.queryByProductId(productId);
        if (StrUtil.isBlank(identifier)) {
            if (ptmSnap.getThingModel().getProperties().stream().anyMatch(t -> Objects.equals(identifier, t.getIdentifier()))) {
                throw new BusinessException("ABILITY_IDENTIFIER_DUPLICATED");
            }
            if (ptmSnap.getThingModel().getServices().stream().anyMatch(t -> Objects.equals(identifier, t.getIdentifier()))) {
                throw new BusinessException("ABILITY_IDENTIFIER_DUPLICATED");
            }
            if (ptmSnap.getThingModel().getEvents().stream().anyMatch(t -> Objects.equals(identifier, t.getIdentifier()))) {
                throw new BusinessException("ABILITY_IDENTIFIER_DUPLICATED");
            }

            ptmSnap.getThingModel().getProperties().add(thingModel.getProperties().get(0));
            ptmSnap.getThingModel().getServices().add(thingModel.getServices().get(0));
            ptmSnap.getThingModel().getEvents().add(thingModel.getEvents().get(0));
        }else {

        }
    }

}
