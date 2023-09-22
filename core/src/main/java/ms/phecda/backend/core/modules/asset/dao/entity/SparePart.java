package ms.phecda.backend.core.modules.asset.dao.entity;

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

import java.util.List;

/**
 * <p>
 * 资产配件
 * </p>
 *
 * @author jscoe
 * @since 2023-06-30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@SuperBuilder
@TableName(value = "asset_spare_part", autoResultMap = true)
public class SparePart extends BaseLogicEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 编号
     */
    private String sn;

    /**
     * 关联资产sn
     */
    private String assetSn;

    /**
     * 是否启用
     */
    @TableField(value = "is_enabled")
    private Boolean enabled;

    /**
     * 备注
     */
    private String remark;


    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> deviceNames;


}