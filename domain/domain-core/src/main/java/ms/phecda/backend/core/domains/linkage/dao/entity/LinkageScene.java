package ms.phecda.backend.core.domains.linkage.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.trionesdev.commons.mybatisplus.entity.BaseLogicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.phecda.backend.core.domains.linkage.dao.mapper.typehandler.ActionsTypeHandler;
import ms.phecda.backend.core.domains.linkage.dao.mapper.typehandler.ScenesTypeHandler;
import ms.phecda.backend.core.domains.linkage.support.rule.Scene;
import ms.phecda.backend.core.domains.linkage.support.rule.action.Action;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "phecda_linkage_scene", autoResultMap = true)
public class LinkageScene extends BaseLogicEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String name;
    private String description;

    @TableField(typeHandler = ScenesTypeHandler.class)
    private List<Scene> scenes;

    @TableField(typeHandler = ActionsTypeHandler.class)
    private List<Action> actions;

    @TableField(value = "is_enabled")
    private Boolean enabled;


}
