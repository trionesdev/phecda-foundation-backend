package ms.triones.backend.core.modules.device.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moensun.commons.mybatisplus.entity.BaseLogicEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "device_product")
public class Product extends BaseLogicEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String name;
    private NodeType nodeType;
    @TableField(value = "thing_model_version")
    private String thingModelVersion;
    @TableField(value = "is_enabled")
    private Boolean enabled;


    @AllArgsConstructor
    public enum NodeType {
        DIRECT("直连设备"),
        GATEWAY("网关设备"),
        GATEWAY_SUB("网关子设备");

        @Getter
        private final String label;
    }
}
