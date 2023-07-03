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
import ms.triones.backend.core.modules.linkage.support.rule.othercondition.OtherCondition;
import ms.triones.backend.core.modules.linkage.support.rule.filter.FilterCondition;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "linkage_scene_condition", autoResultMap = true)
public class LinkageSceneCondition extends BaseLogicEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String sceneId;
    private FilterCondition.TypeEnum type;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private FilterCondition filterCondition;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<List<OtherCondition>> conditions;
    @TableField(value = "is_enabled")
    private Boolean enabled;
}
