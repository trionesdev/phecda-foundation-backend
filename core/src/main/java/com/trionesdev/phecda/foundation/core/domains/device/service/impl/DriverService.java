package com.trionesdev.phecda.foundation.core.domains.device.service.impl;

import com.trionesdev.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.device.manager.impl.DriverManager;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.ProductDriverCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.DriverPO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DriverService {
    private final DriverManager productDriverManager;

    public void createDriver(DriverPO driver) {
        productDriverManager.createDriver(driver);
    }

    public void deleteDriverById(String id) {
        productDriverManager.deleteDriverById(id);
    }

    public void updateDriverById(DriverPO driver) {
        productDriverManager.updateDriverById(driver);
    }

    public Optional<DriverPO> findDriverById(String id) {
        return productDriverManager.findDriverById(id);
    }

    public List<DriverPO> findDriverList(ProductDriverCriteria criteria) {
        return productDriverManager.findDriverList(criteria);
    }

    public PageInfo<DriverPO> findDriverPage(ProductDriverCriteria criteria) {
        return productDriverManager.findDriverPage(criteria);
    }

}
