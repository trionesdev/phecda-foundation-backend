package ms.phecda.backend.core.domains.device.manager.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.moensun.commons.core.page.PageInfo;
import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.domains.device.manager.dto.ProductDTO;
import ms.phecda.backend.core.domains.device.dao.criteria.ProductCriteria;
import ms.phecda.backend.core.domains.device.dao.entity.Product;
import ms.phecda.backend.core.domains.device.dao.entity.ProductThingModelVersion;
import ms.phecda.backend.core.domains.device.dao.entity.enums.ProductStatusEnum;
import ms.phecda.backend.core.domains.device.dao.impl.ProductDAO;
import ms.phecda.backend.core.domains.device.dao.impl.ProductThingModelVersionDAO;
import ms.phecda.backend.core.domains.device.support.DeviceConvertMapper;
import ms.phecda.backend.core.domains.device.thing.model.ThingModel;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductManager {
    private final ProductDAO productDAO;
    private final ProductThingModelVersionDAO productThingModelVersionDAO;

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

    public Optional<ThingModel> findThingModel(String productId) {
        return Optional.ofNullable(productDAO.getById(productId)).map(product -> {
            if (StrUtil.isNotBlank(product.getThingModelVersion())) {
                return productThingModelVersionDAO.selectByProductVersion(productId, product.getThingModelVersion());
            } else {
                return null;
            }
        }).map(ProductThingModelVersion::getThingModel);
    }

    private List<ProductDTO> assembleCollection(List<Product> records) {
        if (CollectionUtil.isEmpty(records)) {
            return Collections.emptyList();
        }
        return DeviceConvertMapper.INSTANCE.productDtoFromRecord(records);
    }

    public void publish(String id) {
        productDAO.updateStatus(id, ProductStatusEnum.RELEASE);
    }

    public void revokePublish(String id) {
        productDAO.updateStatus(id, ProductStatusEnum.DEVELOPMENT);
    }
}
