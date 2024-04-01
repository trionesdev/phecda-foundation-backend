package ms.phecda.backend.core.domains.linkage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.phecda.backend.core.domains.linkage.service.impl.LinkageSceneService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Configuration
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
