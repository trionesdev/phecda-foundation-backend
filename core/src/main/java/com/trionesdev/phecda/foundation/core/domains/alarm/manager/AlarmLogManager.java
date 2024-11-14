package com.trionesdev.phecda.foundation.core.domains.alarm.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.trionesdev.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.entity.AlarmLog;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.entity.enums.DealStatuEnums;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.impl.AlarmLogDAO;
import com.trionesdev.phecda.foundation.core.domains.alarm.dao.criteria.AlarmLogCriteria;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 告警记录 服务类
 * </p>
 *
 * @author jscoe
 * @since 2023-07-11
 */

@Deprecated
@RequiredArgsConstructor
@Service
public class AlarmLogManager {
    private final AlarmLogDAO alarmLogDAO;

    public void create(AlarmLog entity) {
        alarmLogDAO.save(entity);
    }

    public void deleteById(String id) {
        alarmLogDAO.removeById(id);
    }

    public void updateById(AlarmLog entity) {
        alarmLogDAO.updateById(entity);
    }

    public Optional<AlarmLog> queryById(String id) {
        return Optional.ofNullable(alarmLogDAO.getById(id));
    }

    public PageInfo<AlarmLog> queryPage(Integer pageNum, Integer pageSize, AlarmLogCriteria criteria) {
        return alarmLogDAO.selectPage(pageNum, pageSize, criteria);
    }

    public List<AlarmLog> queryList(AlarmLogCriteria criteria) {
        return alarmLogDAO.selectList(criteria);
    }

    public long countAllAlarms() {
        return alarmLogDAO.count();
    }

    public long countNotDealAlarms() {
        LambdaQueryWrapper<AlarmLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AlarmLog::getDealStatus, DealStatuEnums.PENDING);
        return alarmLogDAO.count(wrapper);
    }

    public long countMonthlyAlarms() {
        //获取当前月第一天：
        Calendar begin = Calendar.getInstance();
        begin.setTime(new Date());
        // ca.add(Calendar.MONTH, 0); 此方法可以获取前n月或后n月
        begin.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        begin.set(Calendar.HOUR, 0);
        begin.set(Calendar.MINUTE, 0);
        begin.set(Calendar.SECOND, 0);
        Instant beginTime = begin.getTime().toInstant();
        //获取当前月最后一天：
        Calendar end = Calendar.getInstance();
        end.setTime(new Date());
        end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));
        end.set(Calendar.HOUR, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        Instant endTime = end.getTime().toInstant();

        LambdaQueryWrapper<AlarmLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(AlarmLog::getAlarmTime, beginTime, endTime);
        return alarmLogDAO.count(wrapper);
    }

}
