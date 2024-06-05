package ms.phecda.backend.core.domains.device.service.impl;

import com.trionesdev.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.manager.impl.ProductDriverManager;
import ms.phecda.backend.core.domains.device.repository.criteria.ProductDriverCriteria;
import ms.phecda.backend.core.domains.device.repository.po.ProductDriverPO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductDriverService {
    private final ProductDriverManager productDriverManager;

    public void createDriver(ProductDriverPO driver) {
        productDriverManager.createDriver(driver);
    }

    public void deleteDriverById(String id) {
        productDriverManager.deleteDriverById(id);
    }

    public void updateDriverById(ProductDriverPO driver) {
        productDriverManager.updateDriverById(driver);
    }

    public Optional<ProductDriverPO> findDriverById(String id) {
        return productDriverManager.findDriverById(id);
    }

    public List<ProductDriverPO> findDriverList(ProductDriverCriteria criteria) {
        return productDriverManager.findDriverList(criteria);
    }

    public PageInfo<ProductDriverPO> findDriverPage(ProductDriverCriteria criteria) {
        return productDriverManager.findDriverPage(criteria);
    }

}
