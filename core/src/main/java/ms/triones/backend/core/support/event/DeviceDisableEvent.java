package ms.triones.backend.core.support.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

public class DeviceDisableEvent<T> extends ApplicationEvent implements ResolvableTypeProvider {

    public DeviceDisableEvent(T source) {
        super(source);
    }

    public static <T> DeviceDisableEvent build(T source) {
        return new DeviceDisableEvent(source);
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(getSource()));
    }
}
