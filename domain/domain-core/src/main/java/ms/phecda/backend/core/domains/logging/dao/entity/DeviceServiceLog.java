package ms.phecda.backend.core.domains.logging.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.trionesdev.commons.mybatisplus.entity.BaseLogicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@SuperBuilder
@TableName(value = "logging_device_service_log", autoResultMap = true)
public class DeviceServiceLog extends BaseLogicEntity {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    private String productId;
    private String deviceName;
    private String serviceIdentifier;
    private String serviceName;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private JsonNode inputData;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private JsonNode outputData;
    private Instant time;
}
