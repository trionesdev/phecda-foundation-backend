package ms.triones.backend.core.modules.device.manager.impl;

import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.device.dao.entity.ProductThingModelVersion;
import ms.triones.backend.core.modules.device.dao.impl.ProductThingModelVersionDAO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductThingModelVersionManager {
    private final ProductThingModelVersionDAO productThingModelVersionDAO;

    public Optional<ProductThingModelVersion> findByProductVersion(String productId, String version) {
        return Optional.ofNullable(productThingModelVersionDAO.selectByProductVersion(productId, version));
    }
}
