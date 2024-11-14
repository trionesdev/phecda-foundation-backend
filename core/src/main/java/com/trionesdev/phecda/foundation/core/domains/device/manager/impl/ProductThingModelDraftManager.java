package com.trionesdev.phecda.foundation.core.domains.device.manager.impl;

import com.trionesdev.phecda.foundation.core.domains.device.internal.DeviceDomainConvert;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.ProductThingModelDraft;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.ProductThingModelVersion;
import com.trionesdev.phecda.foundation.core.domains.device.dao.impl.ProductDAO;
import com.trionesdev.phecda.foundation.core.domains.device.dao.impl.ProductThingModelDraftDAO;
import com.trionesdev.phecda.foundation.core.domains.device.dao.impl.ProductThingModelVersionDAO;
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
            ProductThingModelVersion version = DeviceDomainConvert.INSTANCE.from(draft);
            version.setId(null);
            productThingModelVersionDAO.save(version);
            productDAO.updateVersion(productId, version.getId());
        }
    }
}
