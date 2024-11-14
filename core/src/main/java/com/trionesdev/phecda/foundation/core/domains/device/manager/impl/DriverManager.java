package com.trionesdev.phecda.foundation.core.domains.device.manager.impl;

import com.trionesdev.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.ProductDriverCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.impl.DriverDAO;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.DriverPO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DriverManager {
    private final DriverDAO driverDAO;

    public void createDriver(DriverPO driver) {
        driverDAO.save(driver);
    }

    public void deleteDriverById(String id) {
        driverDAO.removeById(id);
    }

    public void updateDriverById(DriverPO driver) {
        driverDAO.updateById(driver);
    }

    public Optional<DriverPO> findDriverById(String id) {
        return Optional.ofNullable(driverDAO.getById(id));
    }

    public Optional<DriverPO> findDriverByName(String name) {
        return Optional.ofNullable(driverDAO.selectByName(name));
    }


    public List<DriverPO> findDriverList(ProductDriverCriteria criteria) {
        return driverDAO.selectList(criteria);
    }

    public PageInfo<DriverPO> findDriverPage(ProductDriverCriteria criteria) {
        return driverDAO.selectPage(criteria);
    }

}
