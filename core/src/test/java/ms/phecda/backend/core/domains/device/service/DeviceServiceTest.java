package ms.phecda.foundation.core.domains.device.service;

import ms.phecda.BaseTest;
import com.trionesdev.phecda.foundation.core.domains.device.dao.criteria.DeviceCriteria;
import com.trionesdev.phecda.foundation.core.domains.device.shared.enums.NodeType;
import com.trionesdev.phecda.foundation.core.domains.device.service.impl.DeviceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DeviceServiceTest extends BaseTest {
    @Autowired
    private DeviceService deviceService;

    @Test
    public void query_test(){
        DeviceCriteria criteria = DeviceCriteria.builder()
                .nodeType(NodeType.DIRECT)
                .productKey("test0001")
                .build();
        deviceService.queryList(criteria);
    }
}
