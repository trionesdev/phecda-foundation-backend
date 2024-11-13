package com.trionesdev.phecda.backend.core.domains.device.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@Accessors(chain = true)
@TableName(value = "phecda_device_statistics_message_daily", autoResultMap = true)
public class DeviceStatisticsMessageDailyPO {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    private String type;
    private LocalDate date;
    private Long quantity;

    public enum Type {
        PROPERTIES_POST,
    }
}
