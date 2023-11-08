package ms.phecda.backend.core.messageaccess.constant;

public class TopicConstants {
    /**
     * （设备端）上报属性TOPIC
     */
    public static final String DEVICE_THING_PROPERTY_POST_TOPIC = "phecda/{productId}/{deviceName}/thing/property/post";
    /**
     * （设备端）上报属性（平台端）响应TOPIC
     */
    public static final String DEVICE_THING_PROPERTY_POST_REPLY = "phecda/{productId}/{deviceName}/thing/property/post_reply";
    /**
     * （设备端）上报事件TOPIC
     */
    public static final String DEVICE_THING_EVENT_POST_TOPIC = "phecda/{productId}/{deviceName}/thing/event/{identifier}/post";
    /**
     * （设备端）上报事件（平台端）响应TOPIC
     */
    public static final String DEVICE_THING_EVENT_POST_REPLY = "phecda/{productId}/{deviceName}/thing/event/{identifier}/post_reply";
    /**
     * （平台端）调用（设备端）服务TOPIC
     */
    public static final String DEVICE_THING_SERVICE = "phecda/{productId}/{deviceName}/thing/service/{identifier}";
    /**
     * （设备端）响应（平台端）服务调用TOPIC
     */
    public static final String DEVICE_THING_SERVICE_REPLY_TOPIC = "phecda/{productId}/{deviceName}/thing/service/{identifier}/reply";
    /**
     * （平台端）设置（设备端）属性值TOPIC
     */
    public static final String DEVICE_THING_SERVICE_PROPERTY_SET = "phecda/{productId}/{deviceName}/thing/service/property/set";

    /**
     * （设备端）上报属性通配符TOPIC
     */
    public static final String DEVICE_THING_PROPERTY_POST_WILDCARD_TOPIC = DEVICE_THING_PROPERTY_POST_TOPIC
            .replaceAll("\\{productId}", "+")
            .replaceAll("\\{deviceName}", "+");

    /**
     * （设备端）上报属性通配符TOPIC
     */
    public static final String DEVICE_THING_SERVICE_REPLY_WILDCARD_TOPIC = DEVICE_THING_SERVICE_REPLY_TOPIC
            .replaceAll("\\{productId}", "+")
            .replaceAll("\\{deviceName}", "+")
            .replaceAll("\\{identifier}", "+");
}
