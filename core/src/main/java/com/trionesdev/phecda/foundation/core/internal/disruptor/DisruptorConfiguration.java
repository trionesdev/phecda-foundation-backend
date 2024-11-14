package com.trionesdev.phecda.foundation.core.internal.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.trionesdev.phecda.foundation.core.internal.disruptor.propertiespost.PropertiesPostEvent;
import com.trionesdev.phecda.foundation.core.internal.disruptor.propertiespost.PropertiesPostEventHandler;
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
        Disruptor<PropertiesPostEvent> disruptor = new Disruptor<>(PropertiesPostEvent::new, bufferSize,
                DaemonThreadFactory.INSTANCE, ProducerType.MULTI, new BlockingWaitStrategy());
        disruptor.handleEventsWith(propertiesPostEventHandler);
        disruptor.setDefaultExceptionHandler(new IgnoreExceptionHandler());
        disruptor.start();
        return disruptor;
    }


}
