package ms.phecda.backend.core.messageaccess.mqtt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@RequiredArgsConstructor
@Component
public class PhecdaMqttApplicationRunner {
    private final MqttConnectOptions mqttConnectOptions;
    private final IMqttAsyncClient mqttAsyncClient;
    private final PhecdaMqttCallback phecdaMqttCallback;
    @PostConstruct
    public void init() {
        connect();
    }

    public void connect() {
        try {
            mqttAsyncClient.setCallback(phecdaMqttCallback);
            mqttAsyncClient.connect(mqttConnectOptions);
        } catch (MqttException e) {
            log.error(e.getMessage(), e);
        }
    }

}
