package com.trionesdev.phecda.foundation.core.domains.messageforwarding.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trionesdev.commons.mybatisplus.po.BaseLogicPO;
import com.trionesdev.phecda.foundation.core.domains.messageforwarding.shared.model.source.SourceProps;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName(value = "phecda_mf_message_source_topic", autoResultMap = true)
public class MessageSourceTopicPO extends BaseLogicPO {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String sourceId;
    private String topic;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private SourceProps properties;

    public String getTopic() {
        return properties.generateTopic();
    }
}
