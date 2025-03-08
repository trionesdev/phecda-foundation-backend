package com.trionesdev.phecda.foundation.core.domains.device.internal.util;

import cn.hutool.core.util.RandomUtil;

import static cn.hutool.core.util.RandomUtil.BASE_CHAR;

public class DeviceUtils {
    public static final String BASE_CHAR_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String productKeyGenerate() {
        return RandomUtil.randomString(BASE_CHAR , 1) + RandomUtil.randomString(9);
    }

}
