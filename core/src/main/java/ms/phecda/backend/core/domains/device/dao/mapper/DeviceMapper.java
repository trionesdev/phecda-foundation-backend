package ms.phecda.backend.core.domains.device.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ms.phecda.backend.core.domains.device.dao.dvo.ProductStatisticsDVO;
import ms.phecda.backend.core.domains.device.dao.entity.Device;
import org.apache.ibatis.annotations.Select;

public interface DeviceMapper extends BaseMapper<Device> {

    @Select("select count(0),\n" +
            "       (select count(0) as unpublishedCount from phecda_device_product where status = 'DEVELOPMENT'),\n" +
            "       (select count(0) as publishedCount from phecda_device_product where status = 'RELEASE')\n" +
            "from phecda_device_product")
    ProductStatisticsDVO selectStatistics();

}
