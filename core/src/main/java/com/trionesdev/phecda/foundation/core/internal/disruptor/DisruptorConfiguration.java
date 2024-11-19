package com.trionesdev.phecda.foundation.core.internal.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import com.trionesdev.phecda.foundation.core.internal.disruptor.message.PhecdaMessageEvent;
import com.trionesdev.phecda.foundation.core.internal.disruptor.message.PhecdaMessageEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class DisruptorConfiguration {
    private final PhecdaMessageEventHandler phecdaMessageEventHandler;

//    @Bean
//    public Disruptor<PropertiesPostEvent> reportPropertyMessageRingBuffer() {
//        int bufferSize = 1024;
//        Disruptor<PropertiesPostEvent> disruptor = new Disruptor<>(PropertiesPostEvent::new, bufferSize,
//                DaemonThreadFactory.INSTANCE, ProducerType.MULTI, new BlockingWaitStrategy());
//        disruptor.handleEventsWith(propertiesPostEventHandler);
//        disruptor.setDefaultExceptionHandler(new IgnoreExceptionHandler());
//        disruptor.start();
//        return disruptor;
//    }

    @Bean
    public Disruptor<PhecdaMessageEvent> phecdaMessageRingBuffer() {
        int bufferSize = 1024;
        Disruptor<PhecdaMessageEvent> disruptor = new Disruptor<>(PhecdaMessageEvent::new, bufferSize,
                DaemonThreadFactory.INSTANCE, ProducerType.MULTI, new BlockingWaitStrategy());
        disruptor.handleEventsWith(phecdaMessageEventHandler);
        disruptor.setDefaultExceptionHandler(new IgnoreExceptionHandler());
        disruptor.start();
        return disruptor;
    }

}
