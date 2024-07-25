package ms.phecda.backend.core.domains.alarm.service.bo;

import lombok.*;
import ms.phecda.backend.core.domains.alarm.dao.entity.enums.AlarmLevelEnum;
import ms.phecda.backend.core.domains.alarm.dao.entity.enums.DealStatuEnums;

import java.time.Instant;
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

    private List<ImageInfoBO> images;

    @Data
    public static class ImageInfoBO {
        private String uid;
        private String name;
        private String url;
        private String imageType;
    }

    private String assetOrAssetSpareName;
    private String assetOrAssetSpareSn;

    private long allAlarms;
    private long notDealAlarms;
    private long monthlyAlarms;
}