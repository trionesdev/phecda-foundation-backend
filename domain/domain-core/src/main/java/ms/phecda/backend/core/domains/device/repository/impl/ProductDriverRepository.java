package ms.phecda.backend.core.domains.device.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trionesdev.commons.core.page.PageInfo;
import com.trionesdev.commons.mybatisplus.util.MpPageUtils;
import ms.phecda.backend.core.domains.device.repository.criteria.ProductDriverCriteria;
import ms.phecda.backend.core.domains.device.repository.mapper.ProductDriverMapper;
import ms.phecda.backend.core.domains.device.repository.po.ProductDriverPO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class ProductDriverRepository extends ServiceImpl<ProductDriverMapper, ProductDriverPO> {

    private LambdaQueryWrapper<ProductDriverPO> buildQueryWrapper(ProductDriverCriteria criteria) {
        LambdaQueryWrapper<ProductDriverPO> wrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(criteria)) {

        }
        return wrapper;
    }

    public ProductDriverPO selectByName(String name) {
        return baseMapper.selectOne(new LambdaQueryWrapper<ProductDriverPO>().eq(ProductDriverPO::getName, name).last(" limit 1"));
    }

    public List<ProductDriverPO> selectList(ProductDriverCriteria criteria) {
        return baseMapper.selectList(buildQueryWrapper(criteria).last(" limit 500 "));
    }

    public PageInfo<ProductDriverPO> selectPage(ProductDriverCriteria criteria) {
        return MpPageUtils.of(baseMapper.selectPage(MpPageUtils.page(criteria), buildQueryWrapper(criteria)));
    }

}
