package ms.triones.backend.core.modules.device.manager.impl;

import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.device.dao.entity.ProductThingModelDraft;
import ms.triones.backend.core.modules.device.dao.impl.ProductThingModelDraftDAO;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductThingModelDraftManager {
    private final ProductThingModelDraftDAO productThingModelDraftDAO;

    public Optional<ProductThingModelDraft> queryByProductId(String productId) {
        return Optional.ofNullable(productThingModelDraftDAO.selectByProductId(productId));
    }

    public void upsertByProductId(ProductThingModelDraft ptm) {
        ProductThingModelDraft ptmSnap = productThingModelDraftDAO.selectByProductId(ptm.getProductId());
        if (Objects.isNull(ptmSnap)) {
            productThingModelDraftDAO.save(ptm);
        } else {
            productThingModelDraftDAO.updateByProductId(ptm);
        }
    }

}
