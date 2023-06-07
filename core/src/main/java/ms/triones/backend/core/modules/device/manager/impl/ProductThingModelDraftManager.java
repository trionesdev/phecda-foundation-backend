package ms.triones.backend.core.modules.device.manager.impl;

import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.device.dao.entity.ProductThingModelDraft;
import ms.triones.backend.core.modules.device.dao.entity.ProductThingModelVersion;
import ms.triones.backend.core.modules.device.dao.impl.ProductDAO;
import ms.triones.backend.core.modules.device.dao.impl.ProductThingModelDraftDAO;
import ms.triones.backend.core.modules.device.dao.impl.ProductThingModelVersionDAO;
import ms.triones.backend.core.modules.device.support.DeviceConvertMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductThingModelDraftManager {
    private final ProductThingModelDraftDAO productThingModelDraftDAO;
    private final ProductThingModelVersionDAO productThingModelVersionDAO;
    private final ProductDAO productDAO;

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

    public void publish(String productId) {
        ProductThingModelDraft draft = productThingModelDraftDAO.selectByProductId(productId);
        if (Objects.nonNull(draft)) {
            ProductThingModelVersion version = DeviceConvertMapper.INSTANCE.from(draft);
            productThingModelVersionDAO.save(version);
            productDAO.updateVersion(productId, version.getId());
        }
    }

}
