package com.trionesdev.phecda.backend.core.domains.device.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trionesdev.commons.mybatisplus.entity.BaseLogicEntity;
import com.trionesdev.commons.mybatisplus.typehandlers.CollectionTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName(value = "phecda_device_device", autoResultMap = true)
public class DevicePO extends BaseLogicEntity  {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String productId;
    private String name;
    private String remarkName;
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

    public static class ProtocolListTypeHandler extends CollectionTypeHandler<Protocol> {
        public ProtocolListTypeHandler(Class<?> type) {
            super(type);
        }

        @Override
        protected Class<Protocol> specificType() {
            return Protocol.class;
        }
    }
}
