package ms.phecda.backend.core.messageaccess.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class DisruptorConfiguration {
    private final ReportPropertyEventHandler reportPropertyEventHandler;

    @Bean
    public RingBuffer<ReportPropertyEvent> reportPropertyMessageRingBuffer() {
        int bufferSize = 1024;
        ReportPropertyEventFactory factory = new ReportPropertyEventFactory();
        Disruptor<ReportPropertyEvent> disruptor = new Disruptor<>(factory, bufferSize,
                DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());
        disruptor.handleEventsWith(reportPropertyEventHandler);
        disruptor.setDefaultExceptionHandler(new IgnoreExceptionHandler());
        return disruptor.start();
    }


}
