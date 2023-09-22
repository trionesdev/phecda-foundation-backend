package ms.phecda.backend.rest.backend.modules.alarm.controller.impl;

import com.moensun.commons.core.page.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.modules.alarm.dao.criteria.AlarmLogCriteria;
import ms.phecda.backend.core.modules.alarm.dao.entity.AlarmLog;
import ms.phecda.backend.core.modules.alarm.service.bo.AlarmLogBO;
import ms.phecda.backend.core.modules.alarm.service.impl.AlarmLogService;
import ms.phecda.backend.rest.backend.modules.alarm.controller.query.AlarmLogQuery;
import ms.phecda.backend.rest.backend.modules.alarm.controller.ro.AlarmLogRO;
import ms.phecda.backend.rest.backend.modules.alarm.support.AlarmConstants;
import ms.phecda.backend.rest.backend.modules.alarm.support.AlarmRestConvertMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 告警记录 前端控制器
 * </p>
 *
 * @author jscoe
 * @since 2023-07-11
 */
@Tag(name = "报警记录")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = AlarmConstants.ALARM_URI)
public class AlarmLogController {
    private final AlarmLogService alarmLogService;

//    @Operation(summary = "新建报警记录")
//    @PostMapping(value = "alarm-logs")
//    public void createAlarmLog(@Validated @RequestBody AlarmLogRO args) {
//        AlarmLog AlarmLog = AlarmRestConvertMapper.INSTANCE.from(args);
//        alarmLogService.create(AlarmLog);
//    }

    @Operation(summary = "根据ID修改报警记录")
    @PutMapping(value = "alarm-logs/{id}")
    public void updateAlarmLogById(
            @PathVariable(value = "id") String id,
            @Validated @RequestBody AlarmLogRO args
    ) {
        AlarmLog alarmLog = AlarmRestConvertMapper.INSTANCE.from(args);
        alarmLog.setId(id);
        alarmLogService.update(alarmLog);
    }

    @Operation(summary = "根据ID删除报警记录")
    @DeleteMapping(value = "alarm-logs/{id}")
    public void deleteAlarmLogById(
            @PathVariable(value = "id") String id
    ) {
        alarmLogService.deleteById(id);
    }

    @Operation(summary = "根据ID获取报警记录")
    @GetMapping(value = "alarm-logs/{id}")
    public AlarmLog queryAlarmLogById(
            @PathVariable(value = "id") String id
    ) {
        return alarmLogService.queryById(id).orElse(null);
    }

    @Operation(summary = "查询报警记录列表(分页)")
    @GetMapping(value = "alarm-logs/page")
    public PageInfo<AlarmLogBO> queryAlarmLogPage(
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            AlarmLogQuery query
    ) {
        AlarmLogCriteria criteria = AlarmRestConvertMapper.INSTANCE.from(query);
        return alarmLogService.queryPage(pageNum, pageSize, criteria);
    }

    @Operation(summary = "查询报警记录列表")
    @GetMapping(value = "alarm-logs/list")
    public List<AlarmLog> queryAlarmLogList(
            AlarmLogQuery query
    ) {
        AlarmLogCriteria criteria = AlarmRestConvertMapper.INSTANCE.from(query);
        return alarmLogService.queryList(criteria);
    }

    @Operation(summary = "查询报警统计")
    @GetMapping(value = "alarm-logs/statistics")
    public AlarmLogBO queryAlarmLogStatistics() {
        return alarmLogService.queryAlarmLogStatistics();
    }
    
}
