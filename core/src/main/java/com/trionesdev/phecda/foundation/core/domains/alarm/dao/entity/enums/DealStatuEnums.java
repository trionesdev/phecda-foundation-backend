package com.trionesdev.phecda.foundation.core.domains.alarm.dao.entity.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum DealStatuEnums {
    PROCESSED("已处理"),
    FALSE_ALARM("误报警"),
    PENDING("待处理");

    @Getter
    private final String desc;
}
