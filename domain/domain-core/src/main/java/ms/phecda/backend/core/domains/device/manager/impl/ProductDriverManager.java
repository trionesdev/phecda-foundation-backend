package ms.phecda.backend.core.domains.device.manager.impl;

import com.trionesdev.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.repository.criteria.ProductDriverCriteria;
import ms.phecda.backend.core.domains.device.repository.impl.ProductDriverRepository;
import ms.phecda.backend.core.domains.device.repository.po.ProductDriverPO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductDriverManager {
    private final ProductDriverRepository productDriverRepository;

    public void createDriver(ProductDriverPO driver) {
        productDriverRepository.save(driver);
    }

    public void deleteDriverById(String id) {
        productDriverRepository.removeById(id);
    }

    public void updateDriverById(ProductDriverPO driver) {
        productDriverRepository.updateById(driver);
    }

    public Optional<ProductDriverPO> findDriverById(String id) {
        return Optional.ofNullable(productDriverRepository.getById(id));
    }

    public Optional<ProductDriverPO> findDriverByName(String name) {
        return Optional.ofNullable(productDriverRepository.selectByName(name));
    }


    public List<ProductDriverPO> findDriverList(ProductDriverCriteria criteria) {
        return productDriverRepository.selectList(criteria);
    }

    public PageInfo<ProductDriverPO> findDriverPage(ProductDriverCriteria criteria) {
        return productDriverRepository.selectPage(criteria);
    }

}
