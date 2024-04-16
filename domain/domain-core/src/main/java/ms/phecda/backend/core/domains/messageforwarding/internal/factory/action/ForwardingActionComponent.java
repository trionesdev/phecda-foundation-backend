package ms.phecda.backend.core.domains.messageforwarding.internal.factory.action;

import ms.phecda.backend.core.domains.messageforwarding.dao.entity.sinkaction.SinkAction;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ForwardingActionComponent {
    SinkAction.TypeEnum type() default SinkAction.TypeEnum.KAFKA ;
}
