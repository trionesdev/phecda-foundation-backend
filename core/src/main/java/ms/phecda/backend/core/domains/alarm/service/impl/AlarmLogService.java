package ms.phecda.backend.core.domains.alarm.service.impl;

import com.trionesdev.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.alarm.dao.criteria.AlarmLogCriteria;
import ms.phecda.backend.core.domains.alarm.dao.entity.AlarmLog;
import ms.phecda.backend.core.domains.alarm.manager.AlarmLogManager;
import ms.phecda.backend.core.domains.alarm.service.bo.AlarmLogBO;
import ms.phecda.backend.core.domains.alarm.support.AlarmLogConvertMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 告警记录 服务类
 * </p>
 *
 * @author jscoe
 * @since 2023-07-11
 */

@RequiredArgsConstructor
@Service
public class AlarmLogService {

    private final AlarmLogManager alarmLogManager;


    public void create(AlarmLog entity) {
        alarmLogManager.create(entity);
    }

    public void deleteById(String id) {
        alarmLogManager.deleteById(id);
    }

    public void update(AlarmLog entity) {
        alarmLogManager.updateById(entity);
    }

    public Optional<AlarmLog> queryById(String id) {
        return alarmLogManager.queryById(id);
    }

    public PageInfo<AlarmLogBO> queryPage(Integer pageNum, Integer pageSize, AlarmLogCriteria criteria) {
        PageInfo<AlarmLog> alarmLogPageInfo = alarmLogManager.queryPage(pageNum, pageSize, criteria);
        List<AlarmLogBO> alarmLogBOs = packageData(alarmLogPageInfo.getRows());
        return PageInfo.<AlarmLogBO>builder()
                .pages(alarmLogPageInfo.getPages())
                .total(alarmLogPageInfo.getTotal())
                .pageNum(alarmLogPageInfo.getPageNum())
                .pageSize(alarmLogPageInfo.getPageSize())
                .rows(alarmLogBOs)
                .build();
    }

    public List<AlarmLog> queryList(AlarmLogCriteria criteria) {
        return alarmLogManager.queryList(criteria);
    }

    private List<AlarmLogBO> packageData(List<AlarmLog> alarmLog) {
        List<AlarmLogBO> alarmLogBOS = AlarmLogConvertMapper.INSTANCE.alarmLogBOFromRecord(alarmLog);
        List<String> assetSns = alarmLogBOS.stream().map(AlarmLogBO::getAssetSn).collect(Collectors.toList());
        List<String> assetSpareSns = alarmLogBOS.stream().map(AlarmLogBO::getAssetSpareSn).collect(Collectors.toList());
//        List<Asset> assets = assetManager.queryList(AssetCriteria.builder().sns(assetSns).build());
//        List<SparePart> spareParts = sparePartManager.queryList(SparePartCriteria.builder().sns(assetSpareSns).build());
//        alarmLogBOS.forEach(alarmLogBO -> {
//            if (StringUtils.isNotBlank(alarmLogBO.getAssetSpareSn())) {
//                List<SparePart> collectTemp = spareParts.stream().filter(v -> v.getSn().equals(alarmLogBO.getAssetSpareSn())).collect(Collectors.toList());
//                if (CollectionUtils.isNotEmpty(collectTemp)) {
//                    alarmLogBO.setAssetOrAssetSpareName(collectTemp.get(0).getName());
//                    alarmLogBO.setAssetOrAssetSpareSn(collectTemp.get(0).getSn());
//                }
//            } else if (StringUtils.isNotBlank(alarmLogBO.getAssetSn())) {
//                List<Asset> collectTemp = assets.stream().filter(v -> v.getSn().equals(alarmLogBO.getAssetSn())).collect(Collectors.toList());
//                if (CollectionUtils.isNotEmpty(collectTemp)) {
//                    alarmLogBO.setAssetOrAssetSpareName(collectTemp.get(0).getName());
//                    alarmLogBO.setAssetOrAssetSpareSn(collectTemp.get(0).getSn());
//                }
//            } else {
//                throw new MSException("此报警记录未关联任何生产设备或配件");
//            }
//        });
//        return alarmLogBOS;
        return Collections.emptyList();
    }

    public AlarmLogBO queryAlarmLogStatistics() {
        long allAlarms = alarmLogManager.countAllAlarms();
        long notDealAlarms = alarmLogManager.countNotDealAlarms();
        long monthlyAlarms = alarmLogManager.countMonthlyAlarms();
        return AlarmLogBO.builder().allAlarms(allAlarms).notDealAlarms(notDealAlarms).monthlyAlarms(monthlyAlarms).build();
    }

}
