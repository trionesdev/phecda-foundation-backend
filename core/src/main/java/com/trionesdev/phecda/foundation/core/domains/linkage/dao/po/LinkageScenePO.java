package com.trionesdev.phecda.foundation.core.domains.linkage.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.trionesdev.commons.mybatisplus.po.BaseLogicPO;
import com.trionesdev.commons.mybatisplus.typehandlers.CollectionTypeHandler;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.Scene;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.ActionTrigger;
import com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action.PhecdaAction;
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
@TableName(value = "phecda_linkage_scene", autoResultMap = true)
public class LinkageScenePO extends BaseLogicPO {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String name;
    private String description;

    @TableField(typeHandler = ScenesTypeHandler.class)
    private List<Scene> scenes;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private ActionTrigger actionTrigger;

    @TableField(typeHandler = ActionsTypeHandler.class)
    private List<PhecdaAction> actions;

    @TableField(value = "is_enabled")
    private Boolean enabled;

    public static class ScenesTypeHandler extends CollectionTypeHandler<Scene> {

        public ScenesTypeHandler(Class<?> type) {
            super(type);
        }

        @Override
        protected Class<Scene> specificType() {
            return Scene.class;
        }
    }


    public static class ActionsTypeHandler extends CollectionTypeHandler<PhecdaAction> {
        public ActionsTypeHandler(Class<?> type) {
            super(type);
        }

        @Override
        protected Class<PhecdaAction> specificType() {
            return PhecdaAction.class;
        }
    }

}
