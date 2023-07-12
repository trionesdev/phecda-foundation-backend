package ms.triones.backend.core.modules.alarm.dao.entity;

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
import ms.triones.backend.core.modules.alarm.dao.entity.enums.AlarmLevelEnum;
import ms.triones.backend.core.modules.alarm.dao.entity.enums.DealStatuEnums;
import ms.triones.backend.core.modules.alarm.dao.entity.enums.ImageTypeEnum;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 告警记录
 * </p>
 *
 * @author jscoe
 * @since 2023-07-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@SuperBuilder
@TableName(value = "alarm_alarm_log", autoResultMap = true)
public class AlarmLog extends BaseLogicEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 告警名称
     */
    private String title;

    /**
     * 告警等级
     */
    private AlarmLevelEnum level;

    /**
     * 告警时间
     */
    private LocalDateTime alarmTime;

    /**
     * 告警描述
     */
    private String describe;

    /**
     * 处理时间
     */
    private LocalDateTime dealTime;

    /**
     * 处理状态
     */
    private DealStatuEnums dealStatus;

    /**
     * 处理备注
     */
    private String dealRemark;

    /**
     * 相机编号
     */
    private String deviceName;

    private String assetSn;

    private String assetSpareSn;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<ImageInfo> images;

    @Data
    public static class ImageInfo {
        private String uid;
        private String name;
        private String url;
        private ImageTypeEnum type;
    }
}