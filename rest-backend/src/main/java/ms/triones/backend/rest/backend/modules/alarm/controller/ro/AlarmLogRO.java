package ms.triones.backend.rest.backend.modules.alarm.controller.ro;

import lombok.*;
import ms.triones.backend.core.modules.alarm.dao.entity.enums.AlarmLevelEnum;

import java.time.Instant;


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
public class AlarmLogRO {

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
    private Instant alarmTime;

    /**
    * 告警描述
    */
    private String describe;

    /**
    * 处理时间
    */
    private Instant dealTime;

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
    private String deviceName;

    private String assetSn;
    private String assetSpareSn;

}