package ms.triones.backend.core.modules;

import cn.hutool.core.util.EnumUtil;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ms.triones.backend.core.modules.device.thing.valuetype.ValueType;
import ms.triones.backend.core.modules.device.thing.valuetype.ValueTypeEnum;

import java.io.IOException;

public class ValueTypeDeserializer extends JsonDeserializer<ValueType> {
    @Override
    public ValueType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectNode objectNode = jsonParser.readValueAs(ObjectNode.class);
        ValueTypeEnum valueTypeName = EnumUtil.fromString(ValueTypeEnum.class, objectNode.findValue("valueType").asText());
        return deserializationContext.readTreeAsValue(objectNode, valueTypeName.getClazz());
    }
}
