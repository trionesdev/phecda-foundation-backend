package ms.triones;

import com.google.common.collect.Lists;
import ms.triones.backend.core.messageaccess.event.DevicePropertyPostEvent;
import ms.triones.backend.core.messageaccess.model.ReadPropertyMessage;
import ms.triones.backend.core.messageaccess.model.ReadPropertyMessage.Reading;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import javax.annotation.Resource;

@SpringBootTest
public class LinkageSceneTest {
    @Resource
    private ApplicationEventPublisher eventPublisher;

    @Test
    public void test() {
        Reading reading = new Reading();
        reading.setIdentifier("particleTotal");
        reading.setValue("50");

        ReadPropertyMessage message = ReadPropertyMessage.builder()
                .deviceName("BaslerCameraJavaDevice01")
                .identifier("particleTotal")
                .thingModelVersion("1692054881446723585")
                .timestamp(System.currentTimeMillis())
                .readings(Lists.newArrayList(reading))
                .build();

        eventPublisher.publishEvent(DevicePropertyPostEvent.build(message));
    }
}
