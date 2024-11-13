package com.trionesdev.phecda.backend.core.domains.device.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.trionesdev.commons.mybatisplus.entity.BaseLogicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@Accessors(chain = true)
@TableName(value = "logging_device_service_log", autoResultMap = true)
public class DeviceServiceLogPO extends BaseLogicEntity {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    private String messageId;
    private String productId;
    private String deviceName;
    private String serviceIdentifier;
    private String serviceName;
    private Boolean sync;
    private String inputData;
    private Result result;
    private String outputData;
    private String errorMessage;
    private Instant time;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, String> tags;


    public enum Result {
        SUCCESS,
        FAILURE
    }
}
