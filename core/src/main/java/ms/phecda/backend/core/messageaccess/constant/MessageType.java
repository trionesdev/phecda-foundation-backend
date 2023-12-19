package ms.phecda.backend.core.messageaccess.constant;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ms.phecda.backend.core.messageaccess.model.BaseDeviceMessage;
import ms.phecda.backend.core.messageaccess.model.DeviceEventMessage;
import ms.phecda.backend.core.messageaccess.model.DeviceEventMessageReply;
import ms.phecda.backend.core.messageaccess.model.ReadPropertyMessage;
import ms.phecda.backend.core.messageaccess.model.ReadPropertyMessageReply;
import ms.phecda.backend.core.messageaccess.model.ServiceInvokeMessage;
import ms.phecda.backend.core.messageaccess.model.ServiceInvokeMessageReply;
import ms.phecda.backend.core.messageaccess.model.WritePropertyMessage;
import ms.phecda.backend.core.messageaccess.model.WritePropertyMessageReply;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public enum MessageType {
    POST_PROPERTY(ReadPropertyMessage.class),
    POST_PROPERTY_REPLY(ReadPropertyMessageReply.class),
    POST_EVENT(DeviceEventMessage.class),
    POST_EVENT_REPLY(DeviceEventMessageReply.class),
    INVOKE_SERVICE(ServiceInvokeMessage.class),
    INVOKE_SERVICE_REPLY(ServiceInvokeMessageReply.class),
    WRITE_PROPERTY(WritePropertyMessage.class),
    WRITE_PROPERTY_REPLY(WritePropertyMessageReply.class),
    UNKNOWN(null);

    @Getter
    private final Class<? extends BaseDeviceMessage> deviceInstance;

    public static Optional<MessageType> of(String messageType) {
        for (MessageType value : values()) {
            if (Objects.equals(value.name(), messageType)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    public static Optional<MessageType> of(Map<String, Object> map) {
        Object msgType = map.get("messageType");
        if (Objects.isNull(msgType)) {
            return Optional.of(UNKNOWN);
        }

        if (msgType instanceof MessageType) {
            return Optional.of((MessageType) msgType);
        } else if (msgType instanceof String) {
            return of(((String) msgType));
        }

        return Optional.of(UNKNOWN);
    }

    public <T extends BaseDeviceMessage> T convert(Map<String, Object> map) {
        Class<? extends BaseDeviceMessage> clazz = deviceInstance;

        if (deviceInstance != null) {
            JSONObject json = new JSONObject(map);
            return (T) json.to(clazz);
        }
        return null;
    }

    public static <T extends BaseDeviceMessage> Optional<T> convertMessage(Map<String, Object> map) {
        return of(map).map(type -> type.convert(map));
    }
}
