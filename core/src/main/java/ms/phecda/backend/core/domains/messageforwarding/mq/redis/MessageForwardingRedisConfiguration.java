package ms.phecda.backend.core.domains.messageforwarding.mq.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@RequiredArgsConstructor
@Configuration
public class MessageForwardingRedisConfiguration implements ApplicationRunner {
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final RuleChangeListener ruleChangeListener;
    private final SinkActionChangeListener sinkActionChangeListener;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        redisMessageListenerContainer.addMessageListener(new MessageListenerAdapter(ruleChangeListener,"onMessage"), PatternTopic.of("message-forwarding-rule-change"));
        redisMessageListenerContainer.addMessageListener(new MessageListenerAdapter(sinkActionChangeListener,"onMessage"), PatternTopic.of("message-sink-change"));
    }
}
