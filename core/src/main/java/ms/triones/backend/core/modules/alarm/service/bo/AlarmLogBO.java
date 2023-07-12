package ms.triones.backend.core.modules.alarm.service.bo;

import lombok.*;
import ms.triones.backend.core.modules.alarm.dao.entity.AlarmLog;

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
@Builder
public class AlarmLogBO {

    private String id;

    /**
    * 告警名称
    */
    private String title;

    /**
    * 告警等级
    */
    private String level;

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
    private String dealStatus;

    /**
    * 处理备注
    */
    private String dealRemark;

    /**
    * 相机编号
    */
    private String deviceSn;

    private List<ImageInfoBO> images;

    @Data
    public static class ImageInfoBO {
        private String uid;
        private String name;
        private String url;
        private String type;
    }

}