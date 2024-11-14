package com.trionesdev.phecda.foundation.core.domains.device.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trionesdev.phecda.foundation.core.domains.device.dao.dvo.DeviceStatisticsDVO;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.DevicePO;
import org.apache.ibatis.annotations.Select;

public interface DeviceMapper extends BaseMapper<DevicePO> {


    @Select("select count(*) as count,\n" +
            "       (select count(*) as enabledCount from phecda_device_device where is_deleted=false and is_enabled=true),\n" +
            "       (select count(*) as disabledCount from phecda_device_device where is_deleted=false and is_enabled=false)\n" +
            "from phecda_device_device where is_deleted=false")
    DeviceStatisticsDVO selectStatusStatistics();
}
