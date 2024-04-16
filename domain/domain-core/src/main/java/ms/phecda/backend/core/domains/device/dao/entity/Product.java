package ms.phecda.backend.core.domains.device.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trionesdev.commons.mybatisplus.entity.BaseLogicEntity;
import com.trionesdev.commons.mybatisplus.typehandlers.CollectionTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.phecda.backend.core.domains.device.dao.entity.enums.AccessChannelEnum;
import ms.phecda.backend.core.domains.device.dao.entity.enums.NodeTypeEnum;
import ms.phecda.backend.core.domains.device.dao.entity.enums.ProductStatusEnum;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "phecda_device_product", autoResultMap = true)
public class Product extends BaseLogicEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String name;
    private String key;
    private NodeTypeEnum nodeType;
    private AccessChannelEnum accessChannel;
    private Type type;
    @TableField(value = "thing_model_version")
    private String thingModelVersion;
    @TableField(typeHandler = ProtocolListTypeHandler.class)
    private List<ProtocolProperty> protocolProperties;
    private ProductStatusEnum status;
    private String driverName;

    @Getter
    @AllArgsConstructor
    public enum Type {

        SENSOR("传感器"),
        CAMERA("摄像头");
        private final String label;
    }


    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProtocolProperty {
        private String name;
        private String label;
    }

    public static class ProtocolListTypeHandler extends CollectionTypeHandler<ProtocolProperty> {
        protected Class<ProtocolProperty> specificType() {
            return ProtocolProperty.class;
        }
    }
}
