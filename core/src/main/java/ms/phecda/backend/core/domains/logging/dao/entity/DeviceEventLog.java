package ms.phecda.backend.core.domains.logging.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.moensun.commons.mybatisplus.entity.BaseLogicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.phecda.backend.core.domains.device.thing.model.ThingModelEvent.Type;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@SuperBuilder
@TableName(value = "logging_device_event_log", autoResultMap = true)
public class DeviceEventLog extends BaseLogicEntity {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    private String productId;
    private String deviceName;
    private String eventIdentifier;
    private String eventName;
    private Type eventType;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private JsonNode outputData;
    private Instant time;
}
