package com.trionesdev.phecda.foundation.core.domains.alarm.dao.po;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@TableName(value = "phecda_alarm_alarm", autoResultMap = true)
public class AlarmPO extends BaseLogicEntity {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    private String type;
    private String level;
    private String productKey;
    private String productName;
    private String deviceName;
    private String description;
    @TableField(typeHandler = EventDataTypeHandler.class)
    private List<Item> eventData;
    private Status status;

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        private String label;
        private String identifier;
        private Object value;
    }

    public static class EventDataTypeHandler extends CollectionTypeHandler<Item> {
        public EventDataTypeHandler(Class<?> type) {
            super(type);
        }
        @Override
        protected Class<Item> specificType() {
            return Item.class;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Status {
        UN_PROCESSED("未处理"),
        MISREPORT("误报"),
        PROCESSED("已经处理");

        private final String label;
    }
}
