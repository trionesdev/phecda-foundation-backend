package ms.triones.backend.core.modules.device.manager.impl;

import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.device.dao.entity.ProductThingModel;
import ms.triones.backend.core.modules.device.dao.impl.ProductThingModelDAO;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductThingModelManager {
    private final ProductThingModelDAO productThingModelDAO;

    public Optional<ProductThingModel> queryByProductId(String productId) {
        return Optional.ofNullable(productThingModelDAO.selectByProductId(productId));
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
