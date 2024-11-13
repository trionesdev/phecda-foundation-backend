package ms.phecda.backend;

import com.trionesdev.phecda.backend.core.internal.util.MqttTopicUtils;
import org.junit.jupiter.api.Test;

import java.time.Duration;

public class MqttTest {

    @Test
    public void match() {
        Boolean match = MqttTopicUtils.isMatched("+/+/thing/property/post", "aas/dd/thing/property/post");
        System.out.println(match);
    }

    @Test
    public void dd_test() throws InterruptedException {
        Duration duration = Duration.ofSeconds(3);
        Thread.sleep(5000);
        System.out.println(duration.getSeconds());
    }

}
