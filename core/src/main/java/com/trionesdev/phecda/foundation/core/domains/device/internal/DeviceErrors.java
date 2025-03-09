package com.trionesdev.phecda.foundation.core.domains.device.internal;

import com.trionesdev.commons.exception.TrionesError;

public class DeviceErrors {

    public static final TrionesError PRODUCT_NOT_FOUND = TrionesError.builder().code("PRODUCT_NOT_FOUND").message("产品不存在").build();
    public static final TrionesError DEVICE_NOT_FOUND = TrionesError.builder().code("DEVICE_NOT_FOUND").message("设备不存在").build();
}
