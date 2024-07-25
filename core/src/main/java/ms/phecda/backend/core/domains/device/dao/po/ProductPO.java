package ms.phecda.backend.core.domains.device.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.trionesdev.commons.mybatisplus.entity.BaseLogicEntity;
import com.trionesdev.commons.mybatisplus.typehandlers.CollectionTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.phecda.backend.core.domains.device.internal.enums.AccessChannel;
import ms.phecda.backend.core.domains.device.internal.enums.NodeType;
import ms.phecda.backend.core.domains.device.internal.enums.ProductStatus;
import ms.phecda.backend.core.domains.device.internal.enums.ProductType;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModel;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "phecda_device_product", autoResultMap = true)
public class ProductPO extends BaseLogicEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String name;
    private String key;
    private String manufacturer;
    private String description;
    private NodeType nodeType;
    private AccessChannel accessChannel;
    private ProductType type;
    @TableField(value = "thing_model_version")
    private String thingModelVersion;
    @TableField(typeHandler = ProtocolListTypeHandler.class)
    private List<ProtocolProperty> protocolProperties;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private ThingModel thingModelDraft;

    private ProductStatus status;
    private String driverName;



    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProtocolProperty {
        private String name;
        private String label;
    }

    public static class ProtocolListTypeHandler extends CollectionTypeHandler<ProtocolProperty> {
        public ProtocolListTypeHandler(Class<?> type) {
            super(type);
        }

        protected Class<ProtocolProperty> specificType() {
            return ProtocolProperty.class;
        }
    }
}
