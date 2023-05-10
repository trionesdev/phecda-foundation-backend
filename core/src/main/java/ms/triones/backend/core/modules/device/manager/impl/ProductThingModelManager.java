package ms.triones.backend.core.modules.device.manager.impl;

import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.device.dao.entity.ProductThingModel;
import ms.triones.backend.core.modules.device.dao.impl.ProductThingModelDAO;
import ms.triones.backend.core.modules.device.thing.model.ThingModel;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ProductThingModelManager {
    private final ProductThingModelDAO productThingModelDAO;

    public ProductThingModel queryByProductId(String productId) {
        return productThingModelDAO.selectByProductId(productId);
    }

    public void upsertByProductId(ProductThingModel ptm) {
        ProductThingModel ptmSnap = productThingModelDAO.selectByProductId(ptm.getProductId());
        if (Objects.isNull(ptmSnap)) {
            productThingModelDAO.save(ptm);
        } else {
            productThingModelDAO.updateByProductId(ptm);
        }
    }

}
