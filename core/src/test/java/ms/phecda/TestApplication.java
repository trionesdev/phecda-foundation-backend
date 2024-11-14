package ms.phecda;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan(value = {"ms.phecda.foundation.core.domains.*.dao.mapper"})
//@EnableFeignClients
public class TestApplication {
    public static void main(String[] args) {
        log.info("--app start--");
        SpringApplication.run(TestApplication.class, args);
        log.info("--app end--");
    }
}
