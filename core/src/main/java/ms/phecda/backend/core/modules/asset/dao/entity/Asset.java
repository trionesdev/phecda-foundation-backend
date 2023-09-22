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
import ms.phecda.backend.core.modules.asset.dao.entity.enums.AssetStateEnum;

import java.util.List;

/**
* <p>
* 资产(生产设备)
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
@TableName(value = "asset_asset", autoResultMap = true)
public class Asset extends BaseLogicEntity {

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
    * 规格型号
    */
    private String specification;

    /**
    * 资产类型code
    */
    private String typeCode;

    /**
     * 关联设备编号
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> deviceNames;

    /**
    * 区域位置code
    */
    private String locationCode;

    /**
    * 当前状态
    */
    private AssetStateEnum state;

    /**
    * 负责部门code
    */
    private String departmentCode;

    /**
    * 负责岗位code
    */
    private String postCode;

    /**
    * 图片集合
    */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<UploadItem> images;

    /**
    * 相关文件集合
    */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<UploadItem> files;

    /**
    * 备注
    */
    private String remark;

    @Data
    public static class UploadItem {
        private String uid;
        private String name;
        private String url;
    }

}