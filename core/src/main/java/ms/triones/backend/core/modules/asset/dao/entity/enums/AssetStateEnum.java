package ms.triones.backend.core.modules.asset.dao.entity.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)

public enum AssetStateEnum {
    SCRAPPED("报废"),
    SHUTDOWN("停用"),
    SHUTDOWN_FOR_REPAIR("停机待修"),
    OPERATION_WITH_FAULTS("带病运行"),
    NORMAL_OPERATION("正常运行");

    @Getter
    private final String desc;
}