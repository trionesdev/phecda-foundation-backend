package ms.phecda.backend.core.bootstrap;

import lombok.RequiredArgsConstructor;
import ms.phecda.backend.core.modules.linkage.service.impl.LinkageSceneService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {
    private final LinkageSceneService linkageSceneService;

    @Override
    public void run(String... args) {
        linkageSceneService.registerAllRules();
    }
}
