package com.trionesdev.phecda.backend.core.domains.alarm.dao.entity.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ImageTypeEnum {
    ALARM("报警图片"),
    DEAL("处理图片");

    @Getter
    private final String desc;
}
