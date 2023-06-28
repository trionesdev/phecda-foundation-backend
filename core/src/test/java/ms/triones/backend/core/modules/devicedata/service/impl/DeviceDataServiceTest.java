package ms.triones.backend.core.modules.devicedata.service.impl;

import com.google.common.collect.Lists;
import ms.triones.BaseTest;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class DeviceDataServiceTest extends BaseTest {

    @Autowired
    private DeviceDataService deviceDataService;

    @Test
    public void insert_test() {
        deviceDataService.insertRecord("default", "deveice1", Instant.now().toEpochMilli(),
                Lists.newArrayList("status"), Lists.newArrayList(TSDataType.BOOLEAN), Lists.newArrayList(true));
        System.out.println("s");
    }

    @Test
    public void queryLastRow_test() {
        List<Map<String, String>> res = deviceDataService.executeLastDataQuery("default", "deveice1");
        System.out.println(res);
    }

}
