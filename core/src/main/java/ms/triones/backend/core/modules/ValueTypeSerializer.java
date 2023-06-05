package ms.triones.backend.core.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ms.triones.backend.core.modules.device.thing.valuetype.ValueType;

import java.io.IOException;
import java.util.Objects;

public class ValueTypeSerializer extends JsonSerializer<ValueType> {
    @Override
    public void serialize(ValueType valueType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (Objects.nonNull(valueType)) {
            jsonGenerator.writeObject(valueType);
        }
    }
}
