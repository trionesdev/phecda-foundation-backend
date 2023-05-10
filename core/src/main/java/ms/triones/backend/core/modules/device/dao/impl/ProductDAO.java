package ms.triones.backend.core.modules.device.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ms.triones.backend.core.modules.device.dao.entity.Product;
import ms.triones.backend.core.modules.device.dao.mapper.ProductMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAO extends ServiceImpl<ProductMapper, Product> {
}
