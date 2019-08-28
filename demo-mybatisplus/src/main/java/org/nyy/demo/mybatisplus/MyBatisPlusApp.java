package org.nyy.demo.mybatisplus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

/**
 * @author niuyy
 * @since 2019-08-28
 */
@SpringBootApplication
@Slf4j
public class MyBatisPlusApp implements CommandLineRunner {

    @Value("${spring.application.name}")
    private String appName;

    public static void main(String[] args) {
        SpringApplication.run(MyBatisPlusApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("{} 启动成功", appName);
    }
}
