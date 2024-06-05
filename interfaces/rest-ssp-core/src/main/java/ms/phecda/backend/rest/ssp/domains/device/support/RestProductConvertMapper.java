package ms.phecda.backend.rest.ssp.domains.device.support;

import com.alibaba.fastjson2.JSONObject;
import ms.phecda.backend.core.domains.device.repository.po.ProductThingModelVersion;
import ms.phecda.backend.core.domains.device.manager.dto.ProductDTO;
import ms.phecda.backend.rest.ssp.sdk.device.rep.ProductRep;
import ms.phecda.backend.rest.ssp.sdk.device.rep.ProductThingModelVersionRep;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Objects;

@Mapper(builder = @Builder(disableBuilder = true))
public interface RestProductConvertMapper {
    RestProductConvertMapper INSTANCE = Mappers.getMapper(RestProductConvertMapper.class);

    List<ProductRep> from(List<ProductDTO> args);

    default ProductThingModelVersionRep from(ProductThingModelVersion args) {
        if (Objects.isNull(args)) {
            return null;
        }

        JSONObject object = JSONObject.from(args);
        String objectStr = object.toJSONString();
        return JSONObject.parseObject(objectStr, ProductThingModelVersionRep.class);
    }
}
