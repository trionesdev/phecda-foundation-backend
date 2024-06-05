package ms.phecda;

import com.trionesdev.csi.minio.annotation.EnableMinioClients;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
@MapperScan(value = {"ms.phecda.backend.core.domains.*.repository.mapper"})
@EnableMinioClients
public class ServeApplication {
    public static void main(String[] args) {
        log.info("--app start--");
        SpringApplication.run(ServeApplication.class, args);
        log.info("--app end--");
    }

}
