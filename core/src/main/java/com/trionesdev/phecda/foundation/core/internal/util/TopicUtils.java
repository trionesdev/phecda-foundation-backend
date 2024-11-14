package com.trionesdev.phecda.foundation.core.internal.util;

import cn.hutool.core.util.StrUtil;

import java.util.Optional;

public class TopicUtils {
    public static String TOPIC_PREFIX = "";

    public static String propertyPostTopic(String productKey, String deviceName) {
        return TOPIC_PREFIX + Optional.ofNullable(productKey).orElse("+") + "/" + Optional.ofNullable(deviceName).orElse("+") + "/thing/property/post";
    }


    public static String eventPostTopic(String productKey, String deviceName) {
        return TOPIC_PREFIX + Optional.ofNullable(productKey).orElse("+") + "/" + Optional.ofNullable(deviceName).orElse("+") + "/thing/event/post";
    }

    /**
     * send command to device
     *
     * @param productKey
     * @param deviceName
     * @param commandName
     * @return
     */
    public static String serviceSendTopic(String productKey, String deviceName, String commandName) {
        return TOPIC_PREFIX + Optional.ofNullable(productKey).orElse("+") + "/" + Optional.ofNullable(deviceName).orElse("+") + "/thing/service/" + commandName;
    }

    public static String serviceAsyncReplyTopic(String messageId) {
        return TOPIC_PREFIX + "thing/service/" + messageId + "/reply/async";
    }

    public static String serviceSyncReplyTopic(String messageId) {
        return TOPIC_PREFIX + "thing/service/" + messageId + "/reply/sync";
    }

    public static String join(String... paths) {
        if (paths.length == 0) {
            return "";
        }
        for (int i = 0; i < paths.length; i++) {
            paths[i] = paths[i].replaceFirst("^/", "");
        }
        return String.join("/", paths);
    }

    public static String removePrefix(String topic, String part) {
        if (StrUtil.isEmpty(part) || !topic.startsWith(part)) {
            return topic;
        }
        return StrUtil.removePrefix(topic, part).replaceFirst("^/", "");
    }
}
