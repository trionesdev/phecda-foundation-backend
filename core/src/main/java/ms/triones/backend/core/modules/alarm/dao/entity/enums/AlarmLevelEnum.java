package ms.triones.backend.core.modules.alarm.dao.entity.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AlarmLevelEnum {

    FIRST_LEVEL("紧急"),
    SECOND_LEVEL("重要"),
    THIRD_LEVEL("一般");

    @Getter
    private final String desc;
}
