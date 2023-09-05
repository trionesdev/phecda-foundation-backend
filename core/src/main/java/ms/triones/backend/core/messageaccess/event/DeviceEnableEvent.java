package ms.triones.backend.core.messageaccess.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

public class DeviceEnableEvent<T> extends ApplicationEvent implements ResolvableTypeProvider {

    public DeviceEnableEvent(T source) {
        super(source);
    }

    public static <T> DeviceEnableEvent build(T source) {
        return new DeviceEnableEvent(source);
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(getSource()));
    }
}
