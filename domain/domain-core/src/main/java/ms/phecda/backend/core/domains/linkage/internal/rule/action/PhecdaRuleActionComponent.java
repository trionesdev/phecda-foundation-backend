package ms.phecda.backend.core.domains.linkage.internal.rule.action;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface PhecdaRuleActionComponent {
    PhecdaAction.TypeEnum type();
}
