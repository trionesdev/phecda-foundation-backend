package com.trionesdev.phecda.foundation.core.domains.messageforwarding.service.factory.action;

import com.trionesdev.phecda.foundation.core.domains.messageforwarding.shared.enums.SinkActionType;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ForwardingActionComponent {
    SinkActionType type() default SinkActionType.KAFKA ;
}
