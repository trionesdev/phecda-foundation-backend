package com.trionesdev.phecda.foundation.rest.ssp.domains.device.support;

import com.alibaba.fastjson2.JSONObject;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.ProductThingModelVersion;
import com.trionesdev.phecda.foundation.core.domains.device.dto.ProductExtDTO;
import com.trionesdev.phecda.foundation.rest.ssp.sdk.device.rep.ProductRep;
import com.trionesdev.phecda.foundation.rest.ssp.sdk.device.rep.ProductThingModelVersionRep;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Objects;

@Mapper(builder = @Builder(disableBuilder = true))
public interface RestProductConvertMapper {
    RestProductConvertMapper INSTANCE = Mappers.getMapper(RestProductConvertMapper.class);

    List<ProductRep> from(List<ProductExtDTO> args);

    default ProductThingModelVersionRep from(ProductThingModelVersion args) {
        if (Objects.isNull(args)) {
            return null;
        }

        JSONObject object = JSONObject.from(args);
        String objectStr = object.toJSONString();
        return JSONObject.parseObject(objectStr, ProductThingModelVersionRep.class);
    }
}
