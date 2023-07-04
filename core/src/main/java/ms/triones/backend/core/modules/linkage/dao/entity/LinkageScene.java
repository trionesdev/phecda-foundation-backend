package ms.triones.backend.core.modules.linkage.dao.entity;

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
import ms.triones.backend.core.modules.linkage.dao.mapper.typehandler.ActionsTypeHandler;
import ms.triones.backend.core.modules.linkage.dao.mapper.typehandler.ConditionsTypeHandler;
import ms.triones.backend.core.modules.linkage.support.rule.action.Action;
import ms.triones.backend.core.modules.linkage.support.rule.filter.FilterCondition;
import ms.triones.backend.core.modules.linkage.support.rule.othercondition.OtherCondition;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "linkage_linkage_scene", autoResultMap = true)
public class LinkageScene extends BaseLogicEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String name;
    private String description;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private FilterCondition filterCondition;
    @TableField(typeHandler = ConditionsTypeHandler.class)
    private List<List<OtherCondition>> conditions;

    @TableField(typeHandler = ActionsTypeHandler.class)
    private List<Action> actions;
    @TableField(value = "is_enabled")
    private Boolean enabled;
}
