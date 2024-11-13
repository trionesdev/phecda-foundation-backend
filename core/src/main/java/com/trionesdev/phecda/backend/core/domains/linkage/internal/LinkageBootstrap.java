package com.trionesdev.phecda.backend.core.domains.linkage.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.trionesdev.phecda.backend.core.domains.linkage.service.impl.LinkageSceneService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(value = {LinkageProperties.class})
public class LinkageBootstrap implements CommandLineRunner {
    private final LinkageSceneService linkageSceneService;

    @Override
    public void run(String... args) {
        CompletableFuture.runAsync(() -> {
            log.info("[LinkageBootstrap] scan linkage rules");
            linkageSceneService.registerAllRules();
        });
    }
}
