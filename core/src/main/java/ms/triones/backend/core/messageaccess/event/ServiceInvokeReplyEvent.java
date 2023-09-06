package ms.triones.backend.core.messageaccess.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

public class ServiceInvokeReplyEvent<T> extends ApplicationEvent implements ResolvableTypeProvider {

    public ServiceInvokeReplyEvent(T source) {
        super(source);
    }

    public static <T> ServiceInvokeReplyEvent build(T source) {
        return new ServiceInvokeReplyEvent(source);
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(getSource()));
    }
}
