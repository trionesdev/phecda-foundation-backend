package com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.impl;

import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.po.AlarmTypePO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.ro.AlarmQueryRO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.criteria.AlarmCriteria;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.po.AlarmLevelPO;
import com.trionesdev.phecda.foundation.core.domains.alarm.manager.dto.AlarmDTO;
import com.trionesdev.phecda.foundation.core.domains.alarm.service.bo.AlarmStatisticsBO;
import com.trionesdev.phecda.foundation.core.domains.alarm.service.impl.AlarmService;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.ro.AlarmLevelQueryRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.ro.AlarmTypeQueryRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.ro.AlarmLevelChangeEnabledRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.ro.AlarmLevelCreateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.ro.AlarmLevelUpdateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.ro.AlarmTypeChangeEnabledRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.ro.AlarmTypeCreateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.controller.ro.AlarmTypeUpdateRO;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.internal.AlarmBeRestConvert;
import com.trionesdev.phecda.foundation.rest.tenant.domains.alarm.internal.AlarmConstants;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "报警记录")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = AlarmConstants.ALARM_URI)
public class AlarmController {
    private final AlarmBeRestConvert alarmBeRestConvert;
    private final AlarmService alarmService;

    //region types
    @Operation(summary = "创建报警类型")
    @PostMapping(value = "types")
    public void createType(@RequestBody AlarmTypeCreateRO args) {
        alarmService.createType(alarmBeRestConvert.from(args));
    }

    @Operation(summary = "删除报警类型")
    @DeleteMapping(value = "types/{typeId}")
    public void deleteTypeById(@PathVariable String typeId) {
        alarmService.deleteTypeById(typeId);
    }

    @Operation(summary = "根据ID修改报警类型")
    @PutMapping(value = "types/{typeId}")
    public void updateTypeById(@PathVariable String typeId, @RequestBody AlarmTypeUpdateRO args) {
        AlarmTypePO alarmType = alarmBeRestConvert.from(args);
        alarmType.setId(typeId);
        alarmService.updateTypeById(alarmType);
    }

    @Operation(summary = "根据ID获取报警类型")
    @GetMapping(value = "types/{typeId}")
    public AlarmTypePO queryTypeById(@PathVariable String typeId) {
        return alarmService.findTypeById(typeId).orElse(null);
    }

    @Operation(summary = "查询报警类型列表")
    @GetMapping(value = "types/list")
    public List<AlarmTypePO> findTypes(AlarmTypeQueryRO query) {
        return alarmService.findTypes(alarmBeRestConvert.from(query));
    }

    @PutMapping(value = "types/{typeId}/enabled")
    public void changeTypeEnabled(@PathVariable String typeId, @RequestBody AlarmTypeChangeEnabledRO args) {
        AlarmTypePO alarmType = AlarmTypePO.builder().id(typeId).enabled(args.getEnabled()).build();
        alarmService.changeTypeEnabledById(alarmType);
    }

    //endregion

    //region levels
    @Operation(summary = "创建报警级别")
    @PostMapping(value = "levels")
    public void createLevel(@RequestBody AlarmLevelCreateRO args) {
        alarmService.createLevel(alarmBeRestConvert.from(args));
    }

    @Operation(summary = "根据ID删除报警级别")
    @DeleteMapping(value = "levels/{levelId}")
    public void deleteLevelById(@PathVariable String levelId) {
        alarmService.deleteLevelById(levelId);
    }

    @Operation(summary = "根据ID修改报警级别")
    @PutMapping(value = "levels/{levelId}")
    public void updateLevelById(@PathVariable String levelId, @RequestBody AlarmLevelUpdateRO args) {
        AlarmLevelPO alarmLevel = alarmBeRestConvert.from(args);
        alarmLevel.setId(levelId);
        alarmService.updateLevelById(alarmLevel);
    }

    @Operation(summary = "根据ID获取报警级别")
    @GetMapping(value = "levels/{levelId}")
    public AlarmLevelPO queryLevelById(@PathVariable String levelId) {
        return alarmService.findLevelById(levelId).orElse(null);
    }

    @Operation(summary = "查询报警级别列表")
    @GetMapping(value = "levels/list")
    public List<AlarmLevelPO> findLevels(AlarmLevelQueryRO query) {
        return alarmService.findLevels(alarmBeRestConvert.from(query));
    }

    @Operation(summary = "修改报警级别启用状态")
    @PutMapping(value = "levels/{levelId}/enabled")
    public void changeLevelEnabled(@PathVariable String levelId, @RequestBody AlarmLevelChangeEnabledRO args) {
        AlarmLevelPO alarmLevel = AlarmLevelPO.builder().id(levelId).enabled(args.getEnabled()).build();
        alarmService.changeLevelEnabledById(alarmLevel);
    }
    //endregion

    @Operation(summary = "查询报警列表（扩展信息）")
    @GetMapping(value = "alarms/ext/list")
    public List<AlarmDTO> findAlarmsExt(AlarmQueryRO query) {
        AlarmCriteria criteria = alarmBeRestConvert.from(query);
        return alarmService.findAlarmsExt(criteria);
    }

    @Operation(summary = "查询报警列表（扩展信息）")
    @GetMapping(value = "alarms/ext/page")
    public PageInfo<AlarmDTO> findAlarmExtPage(
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            AlarmQueryRO query) {
        AlarmCriteria criteria = alarmBeRestConvert.from(query);
        criteria.setPageNum(pageNum);
        criteria.setPageSize(pageSize);
        return alarmService.findAlarmExtPage(criteria);
    }

    @Operation(summary = "报警统计")
    @GetMapping(value = "alarms/statistics")
    public AlarmStatisticsBO queryAlarmStatistics() {
        return alarmService.queryAlarmStatistics();
    }

}
