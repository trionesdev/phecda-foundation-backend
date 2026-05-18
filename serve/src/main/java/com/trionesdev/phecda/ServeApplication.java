package com.trionesdev.phecda;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
@MapperScan(value = {"com.trionesdev.phecda.foundation.core.domains.*.*.mapper"})
public class ServeApplication {
    public static void main(String[] args) {
        log.info("------------Trionesdev Phecda app starting------------");
        SpringApplication.run(ServeApplication.class, args);
        log.info("------------Trionesdev Phecda app started------------");
    }

}
