package ms.phecda.backend.core.domains.device.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ms.phecda.backend.core.domains.device.repository.dvo.ProductStatisticsDVO;
import ms.phecda.backend.core.domains.device.repository.po.ProductPO;
import org.apache.ibatis.annotations.Select;

public interface ProductMapper extends BaseMapper<ProductPO> {
    @Select("select count(0),\n" +
            "       (select count(0) as unpublishedCount from phecda_device_product where  is_deleted=false and status = 'DEVELOPMENT'),\n" +
            "       (select count(0) as publishedCount from phecda_device_product where  is_deleted=false and status = 'RELEASE')\n" +
            "from phecda_device_product where is_deleted=false")
    ProductStatisticsDVO selectStatusStatistics();
}
