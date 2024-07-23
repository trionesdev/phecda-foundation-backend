package ms.phecda.backend.core.domains.device.manager.impl;

import com.trionesdev.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.dao.criteria.ProductDriverCriteria;
import ms.phecda.backend.core.domains.device.dao.impl.DriverDAO;
import ms.phecda.backend.core.domains.device.dao.po.DriverPO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DriverManager {
    private final DriverDAO driverRepository;

    public void createDriver(DriverPO driver) {
        driverRepository.save(driver);
    }

    public void deleteDriverById(String id) {
        driverRepository.removeById(id);
    }

    public void updateDriverById(DriverPO driver) {
        driverRepository.updateById(driver);
    }

    public Optional<DriverPO> findDriverById(String id) {
        return Optional.ofNullable(driverRepository.getById(id));
    }

    public Optional<DriverPO> findDriverByName(String name) {
        return Optional.ofNullable(driverRepository.selectByName(name));
    }


    public List<DriverPO> findDriverList(ProductDriverCriteria criteria) {
        return driverRepository.selectList(criteria);
    }

    public PageInfo<DriverPO> findDriverPage(ProductDriverCriteria criteria) {
        return driverRepository.selectPage(criteria);
    }

}
