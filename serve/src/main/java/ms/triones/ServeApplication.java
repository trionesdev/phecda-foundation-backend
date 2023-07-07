package ms.triones;

import com.moensun.csi.tencentcloud.cos.annotation.EnableTencentCloudCOSClients;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = {"ms.triones", "ms.phecda"})
@MapperScan(value = {"ms.triones.backend.core.modules.*.dao.mapper"})
@EnableTencentCloudCOSClients
//@EnableFeignClients
public class ServeApplication {
    public static void main(String[] args) {
        log.info("--app start--");
        SpringApplication.run(ServeApplication.class, args);
        log.info("--app end--");
    }
}
