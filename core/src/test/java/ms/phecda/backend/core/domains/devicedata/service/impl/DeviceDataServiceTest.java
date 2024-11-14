package ms.phecda.foundation.core.domains.devicedata.service.impl;

import com.google.common.collect.Lists;
import com.trionesdev.phecda.foundation.core.domains.devicedata.service.impl.DeviceDataService;
import ms.phecda.BaseTest;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class DeviceDataServiceTest extends BaseTest {

    @Autowired
    private DeviceDataService deviceDataService;

    @Test
    public void insert_test() {
        deviceDataService.insertRecord("product1", "deveice1", Instant.now().toEpochMilli(),
                Lists.newArrayList("status"), Lists.newArrayList(TSDataType.BOOLEAN), Lists.newArrayList(true));
        System.out.println("s");
    }

    @Test
    public void insert_test2() {
        deviceDataService.insertRecord( "product1", "deveice1", Instant.now().toEpochMilli(),
                Lists.newArrayList("status","maxTemp"), Lists.newArrayList("true","25"));
        System.out.println("s");
    }


    @Test
    public void queryRow_test(){
        List<String> fields = Lists.newArrayList("maxTemp");
        List<Map<String, Object>> res2 =  deviceDataService.queryRawData("deveice1", fields,Instant.now().minus(2, ChronoUnit.DAYS).toEpochMilli(),Instant.now().plus(2, ChronoUnit.DAYS).toEpochMilli());
        System.out.println(res2);
    }

    @Test
    public void queryLastRow_test() {
        List<Map<String, Object>> res2 = deviceDataService.queryLastData("deveice1",Lists.newArrayList("maxTemp"));
        System.out.println(res2);
    }

}
