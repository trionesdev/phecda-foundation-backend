package ms.triones.backend.core.modules.alarm.dao.criteria;

import lombok.*;

import java.time.LocalDateTime;

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
public class AlarmLogCriteria {

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

}