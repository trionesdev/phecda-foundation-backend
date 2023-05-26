package ms.triones.backend.core.modules.device.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moensun.commons.mybatisplus.entity.BaseLogicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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


    public enum NodeType {
        DIRECT,
        GATEWAY,
        GATEWAY_SUB
    }
}
