package ms.phecda.backend.core.domains.linkage.support.rule.action;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface PhecdaRuleActionComponent {
    Action.TypeEnum type();
}
