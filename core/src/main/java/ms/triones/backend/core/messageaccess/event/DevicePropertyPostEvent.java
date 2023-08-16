package ms.triones.backend.core.messageaccess.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

public class DevicePropertyPostEvent<T> extends ApplicationEvent implements ResolvableTypeProvider {

    public DevicePropertyPostEvent(T source) {
        super(source);
    }

    public static <T> DevicePropertyPostEvent build(T source) {
        return new DevicePropertyPostEvent(source);
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(getSource()));
    }
}
