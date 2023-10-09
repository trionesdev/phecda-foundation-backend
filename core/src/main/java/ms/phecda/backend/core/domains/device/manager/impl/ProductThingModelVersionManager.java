package ms.phecda.backend.core.domains.device.manager.impl;

import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.dao.entity.ProductThingModelVersion;
import ms.phecda.backend.core.domains.device.dao.impl.ProductThingModelVersionDAO;
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
