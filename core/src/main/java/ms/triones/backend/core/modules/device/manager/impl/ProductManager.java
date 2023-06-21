package ms.triones.backend.core.modules.device.manager.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.moensun.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.device.dao.criteria.ProductCriteria;
import ms.triones.backend.core.modules.device.dao.entity.Product;
import ms.triones.backend.core.modules.device.dao.impl.ProductDAO;
import ms.triones.backend.core.modules.device.manager.dto.ProductDTO;
import ms.triones.backend.core.modules.device.support.DeviceConvertMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductManager {
    private final ProductDAO productDAO;

    public void create(Product product) {
        productDAO.save(product);
    }

    public void deleteById(Product product) {
        productDAO.removeById(product);
    }

    public void updateById(Product product) {
        productDAO.updateById(product);
    }

    public Optional<Product> queryById(String id) {
        return Optional.ofNullable(productDAO.getById(id));
    }

    public Optional<ProductDTO> queryExtById(String id) {
        return Optional.ofNullable(productDAO.getById(id)).map(DeviceConvertMapper.INSTANCE::fromRecord);
    }
    
    public List<ProductDTO> queryAllByIds(Collection<String> ids) {
        return assembleCollection(productDAO.listByIds(ids));
    }

    public List<Product> queryList(ProductCriteria criteria) {
        return productDAO.selectList(criteria);
    }

    public PageInfo<Product> queryPage(Integer pageNum, Integer pageSize, ProductCriteria criteria) {
        return productDAO.selectPage(pageNum, pageSize, criteria);
    }

    private List<ProductDTO> assembleCollection(List<Product> records) {
        if (CollectionUtil.isEmpty(records)) {
            return Collections.emptyList();
        }
        return DeviceConvertMapper.INSTANCE.productDtoFromRecord(records);
    }
}
