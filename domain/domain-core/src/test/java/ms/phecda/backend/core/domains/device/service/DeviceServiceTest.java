package ms.phecda.backend.core.domains.device.service;

import ms.phecda.BaseTest;
import ms.phecda.backend.core.domains.device.dao.criteria.DeviceCriteria;
import ms.phecda.backend.core.domains.device.dao.entity.enums.NodeTypeEnum;
import ms.phecda.backend.core.domains.device.service.impl.DeviceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DeviceServiceTest extends BaseTest {
    @Autowired
    private DeviceService deviceService;

    @Test
    public void query_test(){
        DeviceCriteria criteria = DeviceCriteria.builder()
                .nodeType(NodeTypeEnum.DIRECT)
                .productKey("test0001")
                .build();
        deviceService.queryList(criteria);
    }
}
