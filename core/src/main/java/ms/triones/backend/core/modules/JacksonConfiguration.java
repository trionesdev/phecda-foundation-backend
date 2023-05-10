package ms.triones.backend.core.modules;

import com.moensun.commons.core.jackson.deserializer.InstantDeserializer;
import com.moensun.commons.core.jackson.serializer.InstantSerializer;
import com.moensun.commons.core.jackson.serializer.LongSerializer;
import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.modules.device.thing.valuetype.ValueType;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.time.Instant;

@RequiredArgsConstructor
@Configuration
public class JacksonConfiguration {


    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.serializerByType(BigInteger.class, LongSerializer.instance);
            builder.serializerByType(Long.class, LongSerializer.instance);
            builder.serializerByType(Long.TYPE, LongSerializer.instance);
            builder.serializerByType(Instant.class, new InstantSerializer());
            builder.deserializerByType(Instant.class, new InstantDeserializer());
            builder.deserializerByType(ValueType.class, new ValueTypeDeserializer());
        };
    }

}
