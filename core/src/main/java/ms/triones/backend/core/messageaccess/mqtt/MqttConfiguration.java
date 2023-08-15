package ms.triones.backend.core.messageaccess.mqtt;

import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class MqttConfiguration implements CommandLineRunner {
    private final IMqttAsyncClient mqttAsyncClient;
    private final MqttPhecdaMessageListener mqttPhecdaMessageListener;

    @Override
    public void run(String... args) throws Exception {
        mqttAsyncClient.subscribe("/phecda/edge/message",1,mqttPhecdaMessageListener);
    }
}
