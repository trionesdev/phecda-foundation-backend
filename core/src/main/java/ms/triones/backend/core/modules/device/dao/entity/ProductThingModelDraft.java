package ms.triones.backend.core.modules.device.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moensun.commons.mybatisplus.entity.BaseLogicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.triones.backend.core.modules.device.thing.model.ThingModel;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "device_product_thing_model_draft", autoResultMap = true)
public class ProductThingModelDraft extends BaseLogicEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String productId;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private ThingModel thingModel = new ThingModel();

    public static class ThingModelTypeHandler extends BaseTypeHandler<ThingModel> {
        private static ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void setNonNullParameter(PreparedStatement ps, int i, ThingModel parameter, JdbcType jdbcType) throws SQLException {
            ps.setString(i, toJsonString(parameter));
        }

        @Override
        public ThingModel getNullableResult(ResultSet rs, String columnName) throws SQLException {
            final String json = rs.getString(columnName);
            return StringUtils.isBlank(json) ? null : parse(json);
        }

        @Override
        public ThingModel getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
            final String json = rs.getString(columnIndex);
            return StringUtils.isBlank(json) ? null : parse(json);
        }

        @Override
        public ThingModel getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
            final String json = cs.getString(columnIndex);
            return StringUtils.isBlank(json) ? null : parse(json);
        }

        protected ThingModel parse(String json) {
            try {
                ThingModel thingModel = objectMapper.readValue(json, ThingModel.class);

                thingModel.getServices();



                return objectMapper.readValue(json, ThingModel.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        protected String toJsonString(ThingModel obj) {
            try {
                return objectMapper.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
