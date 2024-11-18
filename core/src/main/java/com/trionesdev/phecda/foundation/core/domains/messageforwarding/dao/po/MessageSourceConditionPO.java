package com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trionesdev.commons.mybatisplus.po.BaseLogicPO;
import com.trionesdev.commons.mybatisplus.typehandlers.CollectionTypeHandler;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.ProductPO.ProtocolProperty;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.model.source.MessageSourceConditionItem;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.internal.model.source.MessageType;
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
@TableName(value = "phecda_mf_message_source_condition", autoResultMap = true)
public class MessageSourceConditionPO extends BaseLogicPO {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String sourceId;
    private MessageType type;
    @TableField(typeHandler = MessageSourceConditionItemsTypeHandler.class)
    private List<MessageSourceConditionItem> items;

    public static class MessageSourceConditionItemsTypeHandler extends CollectionTypeHandler<MessageSourceConditionItem> {
        public MessageSourceConditionItemsTypeHandler(Class<?> type) {
            super(type);
        }

        protected Class<MessageSourceConditionItem> specificType() {
            return MessageSourceConditionItem.class;
        }
    }
}
