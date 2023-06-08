package ms.triones.backend.core.modules.device.manager.impl;

import com.moensun.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.device.dao.criteria.ProductCriteria;
import ms.triones.backend.core.modules.device.dao.entity.Product;
import ms.triones.backend.core.modules.device.dao.impl.ProductDAO;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    public List<Product> queryAllByIds(Collection<String> ids) {
        return productDAO.listByIds(ids);
    }

    public List<Product> queryList(ProductCriteria criteria){
        return productDAO.selectList(criteria);
    }

    public PageInfo<Product> queryPage(Integer pageNum, Integer pageSize, ProductCriteria criteria) {
        return productDAO.selectPage(pageNum, pageSize, criteria);
    }

}
