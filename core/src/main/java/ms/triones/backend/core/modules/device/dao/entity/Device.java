package ms.triones.backend.core.modules.device.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.moensun.commons.mybatisplus.entity.BaseLogicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.triones.backend.core.modules.device.dao.entity.Device.Protocol;
import ms.triones.infrastructure.conf.mybatisplus.SpecialListTypeHandler;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "device_device", autoResultMap = true)
public class Device extends BaseLogicEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String productId;
    private String name;
    private String remarkName;
    private String nodeId;
    private String gatewayId;
    @TableField(typeHandler = ProtocolListTypeHandler.class)
    private List<Protocol> protocols;
    @TableField(value = "is_activated")
    private Boolean activated;
    @TableField(value = "is_enabled")
    private Boolean enabled;

    @Data
    public static class Protocol {
        private String name;
        private String value;
    }

    public static class ProtocolListTypeHandler extends SpecialListTypeHandler<Protocol> {
        @Override
        public Class<Protocol> specialType() {
            return Protocol.class;
        }
    }
}
