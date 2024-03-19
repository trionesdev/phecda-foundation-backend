package ms.phecda.backend.core.messageaccess.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.messageaccess.disruptor.propertiespost.PropertiesPostEvent;
import ms.phecda.backend.core.messageaccess.disruptor.propertiespost.PropertiesPostEventFactory;
import ms.phecda.backend.core.messageaccess.disruptor.propertiespost.PropertiesPostEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class DisruptorConfiguration {
    private final PropertiesPostEventHandler propertiesPostEventHandler;

    @Bean
    public Disruptor<PropertiesPostEvent> reportPropertyMessageRingBuffer() {
        int bufferSize = 1024;
        PropertiesPostEventFactory factory = new PropertiesPostEventFactory();
        Disruptor<PropertiesPostEvent> disruptor = new Disruptor<>(factory, bufferSize,
                DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());
        disruptor.handleEventsWith(propertiesPostEventHandler);
        disruptor.setDefaultExceptionHandler(new IgnoreExceptionHandler());
        disruptor.start();
        return disruptor;
    }


}
