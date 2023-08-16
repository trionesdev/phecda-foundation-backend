package ms.triones.backend.core.messageaccess;

import lombok.RequiredArgsConstructor;
import ms.triones.backend.core.messageaccess.mqtt.PhecdaMqttManager;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MessageAccessApplicationRunner implements ApplicationRunner {
    private final PhecdaMqttManager phecdaMqttManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        phecdaMqttManager.init();
    }
}
