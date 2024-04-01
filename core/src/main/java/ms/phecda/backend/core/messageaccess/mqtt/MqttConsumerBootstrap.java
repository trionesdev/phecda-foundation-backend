package ms.phecda.backend.core.messageaccess.mqtt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class MqttConsumerBootstrap implements CommandLineRunner {
    private final MqttConnectOptions mqttConnectOptions;
    private final IMqttAsyncClient mqttAsyncClient;
    private final PhecdaMqttCallback phecdaMqttCallback;

    public void connect() {
        try {
            mqttAsyncClient.setCallback(phecdaMqttCallback);
            mqttAsyncClient.connect(mqttConnectOptions);
        } catch (MqttException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        CompletableFuture.runAsync(() -> {
            connect();
        });
    }
}
