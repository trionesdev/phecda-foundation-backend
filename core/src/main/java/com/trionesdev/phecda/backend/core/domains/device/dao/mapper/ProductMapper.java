package com.trionesdev.phecda.backend.core.domains.device.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trionesdev.phecda.backend.core.domains.device.dao.dvo.ProductStatisticsDVO;
import com.trionesdev.phecda.backend.core.domains.device.dao.po.ProductPO;
import org.apache.ibatis.annotations.Select;

public interface ProductMapper extends BaseMapper<ProductPO> {
    @Select("select count(0),\n" +
            "       (select count(0) as unpublishedCount from phecda_device_product where  is_deleted=false and status = 'DEVELOPMENT'),\n" +
            "       (select count(0) as publishedCount from phecda_device_product where  is_deleted=false and status = 'RELEASE')\n" +
            "from phecda_device_product where is_deleted=false")
    ProductStatisticsDVO selectStatusStatistics();
}
