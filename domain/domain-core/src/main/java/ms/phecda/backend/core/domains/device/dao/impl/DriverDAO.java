package ms.phecda.backend.core.domains.device.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.mybatisplus.util.MpPageUtils;
import ms.phecda.backend.core.domains.device.dao.criteria.ProductDriverCriteria;
import ms.phecda.backend.core.domains.device.dao.mapper.ProductDriverMapper;
import ms.phecda.backend.core.domains.device.dao.po.DriverPO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class DriverDAO extends ServiceImpl<ProductDriverMapper, DriverPO> {

    private LambdaQueryWrapper<DriverPO> buildQueryWrapper(ProductDriverCriteria criteria) {
        LambdaQueryWrapper<DriverPO> wrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(criteria)) {

        }
        return wrapper;
    }

    public DriverPO selectByName(String name) {
        return baseMapper.selectOne(new LambdaQueryWrapper<DriverPO>().eq(DriverPO::getName, name).last(" limit 1"));
    }

    public List<DriverPO> selectList(ProductDriverCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria).last(" limit 500 "));
    }

    public PageInfo<DriverPO> selectPage(ProductDriverCriteria criteria) {
        return MpPageUtils.of(baseMapper.selectPage(MpPageUtils.page(criteria), buildQueryWrapper(criteria)));
    }

}
