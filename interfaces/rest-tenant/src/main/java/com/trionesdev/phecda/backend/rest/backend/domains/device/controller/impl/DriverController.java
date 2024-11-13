package com.trionesdev.phecda.backend.rest.backend.domains.device.controller.impl;

import com.trionesdev.commons.core.page.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.backend.core.domains.device.dao.criteria.ProductDriverCriteria;
import com.trionesdev.phecda.backend.core.domains.device.dao.po.DriverPO;
import com.trionesdev.phecda.backend.core.domains.device.service.impl.DriverService;
import com.trionesdev.phecda.backend.rest.backend.domains.device.controller.query.ProductDriverQuery;
import com.trionesdev.phecda.backend.rest.backend.domains.device.controller.ro.DriverRO;
import com.trionesdev.phecda.backend.rest.backend.domains.device.internal.DeviceBeRestConvert;
import com.trionesdev.phecda.backend.rest.backend.domains.device.internal.DeviceConstants;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "驱动")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = DeviceConstants.DEVICE_URI)
public class DriverController {
    private final DeviceBeRestConvert convert;
    private final DriverService driverService;

    @Operation(summary = "新建驱动")
    @PostMapping(value = "drivers")
    public void createDriver(@Validated @RequestBody DriverRO.Create args) {
        var driver = convert.from(args);
        driverService.createDriver(driver);
    }

    @Operation(summary = "根据ID删除驱动")
    @DeleteMapping(value = "drivers/{id}")
    public void deleteDriverById(@PathVariable String id) {
        driverService.deleteDriverById(id);
    }

    @Operation(summary = "根据ID修改驱动")
    @PutMapping(value = "drivers/{id}")
    public void updateDriverById(@PathVariable String id, @Validated @RequestBody DriverRO.Update args) {
        var driver = convert.from(args);
        driver.setId(id);
        driverService.updateDriverById(driver);
    }

    @Operation(summary = "根据ID获取驱动")
    @GetMapping(value = "drivers/{id}")
    public DriverPO findDriverById(@PathVariable String id) {
        return driverService.findDriverById(id).orElse(null);
    }

    @Operation(summary = "获取驱动列表")
    @GetMapping(value = "drivers/list")
    public List<DriverPO> findDriverList(ProductDriverQuery query) {
        ProductDriverCriteria criteria = convert.from(query);
        return driverService.findDriverList(criteria);
    }

    @Operation(summary = "查询驱动列表(分页)")
    @GetMapping(value = "drivers/page")
    public PageInfo<DriverPO> findDriverPage(
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            ProductDriverQuery query
    ) {
        ProductDriverCriteria criteria = convert.from(query);
        criteria.setPageNum(pageNum);
        criteria.setPageSize(pageSize);
        return driverService.findDriverPage(criteria);
    }

}
